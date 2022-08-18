package com.ratepay.bugtracker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@OpenAPIDefinition(info = @Info(title = "Swagger", version = "v3.0"))
@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {
	private final Environment env;

	@Bean
	public OpenAPI newOpenAPI() {
		return new OpenAPI()
				.info(new io.swagger.v3.oas.models.info.Info().title(env.getProperty("spring.application.name"))
						.description(env.getProperty("project.description"))
						.version(env.getProperty("project.version"))).addServersItem(new Server().url(env.getProperty("spring.mvc.servlet.path")));
	}

}
