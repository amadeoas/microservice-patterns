package com.satish.central.docs.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class PersonApplication {

	public static void main(final String[] args) {
		SpringApplication.run(PersonApplication.class, args);
	}

}
