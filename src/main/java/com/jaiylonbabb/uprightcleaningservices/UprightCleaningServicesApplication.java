package com.jaiylonbabb.uprightcleaningservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.jaiylonbabb.uprightcleaningservices")
public class UprightCleaningServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UprightCleaningServicesApplication.class, args);
	}

}
