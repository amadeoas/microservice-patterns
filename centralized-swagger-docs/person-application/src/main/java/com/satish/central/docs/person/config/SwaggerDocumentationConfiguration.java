package com.satish.central.docs.person.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerDocumentationConfiguration {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerDocumentationConfiguration.class);

    @Value("${project.contact.name}")
    private String contactName;
    @Value("${project.contact.url}")
    private String contactUrl;
    @Value("${project.contact.email}")
    private String contactEmail;
    @Value("${project.description}")
    private String description;
    @Value("${project.title}")
    private String title;
    @Value("${project.termsServiceUrl}")
    private String termsServiceUrl;
    @Value("${project.version}")
    private String version;

    @Autowired
    private BuildProperties buildProperties;
    @Autowired
    private Environment environment;


    public void showInfo() {
        LOGGER.info("Application \"{}\", version {}, (built on \"{}\", active profile \"{}\", artifact ID \"{}\", and group \"{}\")", 
                this.buildProperties.getName(), this.buildProperties.getVersion(), 
                this.buildProperties.getTime(), getActiveProfile(), 
                this.buildProperties.getArtifact(), this.buildProperties.getGroup());
    }

    ApiInfo apiInfo() {
        showInfo();

        return new ApiInfoBuilder()
		        .title(this.title)
				.description(this.description)
				.termsOfServiceUrl(this.termsServiceUrl).version(this.version)
				.contact(new Contact(this.contactName, this.contactUrl, this.contactEmail))
				.build();
	}

	@Bean
	public Docket configureControllerPackageAndConvertors() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .select()
				.apis(RequestHandlerSelectors.basePackage("com.satish.central.docs.person.web.rest.resource"))
				.build()
				.apiInfo(apiInfo());
	}
    
    private final String getActiveProfile() {
        String[] activeProfiles = this.environment.getActiveProfiles();

        if (activeProfiles == null || activeProfiles.length == 0) {
            activeProfiles = this.environment.getDefaultProfiles();
        }

        return activeProfiles[0];
    }

}
