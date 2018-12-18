package com.satish.central.docs.config.swagger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author satish sharma
 * 
 * <pre>
 *   Periodically poll the service instances and update the in memory store as key value pair.
 * </pre>
 */
@Component
public class ServiceDescriptionUpdater {

	private static final Logger logger = LoggerFactory.getLogger(ServiceDescriptionUpdater.class);

	@Value("${swagger.default.url}")
	private final String defaultSwaggerUrl = null;
	private static final String KEY_SWAGGER_URL="swagger_url";
	@Value("${eureka.instance.appname}")
	private final String ownAppName = null;

	@Autowired
	private DiscoveryClient discoveryClient;
    @Autowired
    private ServiceDefinitionsContext definitionContext;
    /**
     * REST template instance to query the services.
     */
    private final RestTemplate template;
	

	public ServiceDescriptionUpdater() {
		this.template = new RestTemplate();
	}

    /**
     * Refreshes the Swagger configuration.
     */
	@Scheduled(fixedDelayString= "${swagger.config.refreshrate}")
	public void refreshSwaggerConfigurations() {
		logger.debug("Refreshing Service Definition Context...");		
		this.discoveryClient.getServices().stream().forEach(serviceName -> {
			logger.debug("Attempting service definition refresh for service name '{}'", serviceName);

			final List<ServiceInstance> serviceInstances 
			        = this.discoveryClient.getInstances(serviceName);

			if (serviceInstances == null || serviceInstances.isEmpty()) {
			    // Should not be the case kept for fail safe
			    if (!this.ownAppName.equals(serviceName)) {
			        logger.info("No instances available for service with name '{}'", serviceName);
			    } else {
                    logger.debug("No own instances available for service with name '{}'", 
                            serviceName);
			    }
			} else {
				final ServiceInstance instance = serviceInstances.get(0);
				final String swaggerURL = getSwaggerURL(instance);
				final Optional<Object> jsonData 
				        = getSwaggerDefinitionForAPI(serviceName, swaggerURL);

				if (jsonData.isPresent()) {
					final String content = getJSON(serviceName, jsonData.get());

					this.definitionContext.addServiceDefinition(serviceName, content);
				} else {
					logger.error("Error, skipping service with name '{}': Could not get Swagegr definition from API", 
					        serviceName);
				}
				logger.info("Service definition Context Refreshed at {}", LocalDate.now());
			}
		});
	}

    /**
     * @param serviceName the service name the data belongs to.
     * @param data an object.
     * @return the JSON string representation of the passed data and empty string if any failure.
     */
    public String getJSON(final String serviceName, final Object data) {
        try {
            return new ObjectMapper().writeValueAsString(data);
        } catch (final JsonProcessingException jpe) {
            logger.error("Error for service with name '{}': {}", serviceName, jpe.getMessage(), 
                    jpe);

            return "";
        }
    }

    /**
     * @param instance the service instance.
     * @return the swagger URL for the provided service.
     */
	private String getSwaggerURL(final ServiceInstance instance) {
		final String swaggerURL = instance.getMetadata().get(KEY_SWAGGER_URL);

		return (swaggerURL != null) ? (instance.getUri() + swaggerURL) 
		        : (instance.getUri() + this.defaultSwaggerUrl);
	}

    /**
     * @param serviceName the name of the service.
     * @param url the URL to access the specified service.
     * @return the swagger definition for the specified service by its name.
     */
	private Optional<Object> getSwaggerDefinitionForAPI(
	        final String serviceName, 
	        final String url) {
		logger.debug("Accessing the Swagger definition JSON for service with name '{}', and URL '{}'...", 
		        serviceName, url);
		try {
			final Object jsonData = this.template.getForObject(url, Object.class);
			final Optional<Object> optional = Optional.of(jsonData);

			logger.debug("Swagger definition JSON for service with name '{}', and URL '{}' was successfully obtained", 
			        serviceName, url);

			return optional;
		} catch (final RestClientException rce) {
			logger.error("Error while getting service definition for service with name '{}': {}", 
			        serviceName, rce.getMessage(), rce);

			return Optional.empty();
		}
	}

}
