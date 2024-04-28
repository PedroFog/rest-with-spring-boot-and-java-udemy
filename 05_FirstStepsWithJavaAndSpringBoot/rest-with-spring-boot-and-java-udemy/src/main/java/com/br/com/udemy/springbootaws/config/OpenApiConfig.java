package com.br.com.udemy.springbootaws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
					.title("Curso Udemy Restful API to Aws")
					.version("v1")
					.description("Udemy Study")
					.termsOfService("urlTerms ")
					.license(new License().name("Apache 2.0").url("localhost")));
	}

}
