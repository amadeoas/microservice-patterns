package com.satish.central.docs.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;


/**
 * @author satish sharma
 * 
 * <pre>
 *  Swagger UI configurations. Configure bean of the {@link SwaggerResourcesProvider} to read data 
 *  from in-memory context.
 * </pre>
 */
@Configuration
public class SwaggerUIConfiguration {

	@Autowired
	private ServiceDefinitionsContext definitionContext;


	/**
	 * @return the REST template.
	 */
	@Bean
	public RestTemplate configureTemplate() {
		return new RestTemplate();
	}

	/**
	 * Provides the Swagger resource provider associated with this application.
	 * 
	 * @param defaultResourcesProvider the provider of the resources to use.
	 * @param template the REST template.
	 * @return the resource provider.
	 */
    @Primary
    @Bean
    @Lazy
    public SwaggerResourcesProvider swaggerResourcesProvider(
            final InMemorySwaggerResourcesProvider defaultResourcesProvider, 
            final RestTemplate template) {
        return () -> {
            final List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());

            resources.clear();
            resources.addAll(this.definitionContext.getSwaggerDefinitions());

            return resources;
        };
    }

}
