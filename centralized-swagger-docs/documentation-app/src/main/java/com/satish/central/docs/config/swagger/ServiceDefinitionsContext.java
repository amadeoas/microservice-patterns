package com.satish.central.docs.config.swagger;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;


/**
 * @author satish sharma
 * 
 * <pre>
 *   	In-Memory store to hold API-Definition JSONs.
 * </pre>
 */
@Component
@Scope(scopeName=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceDefinitionsContext {
	
    /**
     * Map of service name and service description.
     */
	private final ConcurrentHashMap<String, String> serviceDescriptions; 
	 

	private ServiceDefinitionsContext() {
		this.serviceDescriptions = new ConcurrentHashMap<>();
	}

    /**
     * Adds the specified service as registered.
     * 
     * @param serviceName the name of the registered service.
     * @param serviceDescription the description of the registered service.
     */
	public void addServiceDefinition(final String serviceName, final String serviceDescription) {
		this.serviceDescriptions.put(serviceName, serviceDescription);
	}

	/**
	 * @param serviceName the service name.
	 * @return the description for the specified service name, if it's registered.
	 */
	public String getSwaggerDefinition(final String serviceName) {
		return this.serviceDescriptions.get(serviceName);
	}

	/**
	 * @return list of all the registered API service definitions.
	 */
	public List<SwaggerResource> getSwaggerDefinitions() {
		return this.serviceDescriptions.entrySet().stream().map(serviceDefinition -> {
		        final SwaggerResource resource = new SwaggerResource();

			    resource.setLocation("/service/" + serviceDefinition.getKey());
			    resource.setName(serviceDefinition.getKey());
			    resource.setSwaggerVersion("2.0");	 

			    return resource;
			}).collect(Collectors.toList());
	}

}
