package com.travel.aroundthekorea.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.travel.aroundthekorea.config.constant.OpenApiProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Profile({"local", "prod"})
@EnableConfigurationProperties(OpenApiProperties.class)
@Configuration
public class SwaggerConfig {
	private final OpenApiProperties properties;

	public SwaggerConfig(OpenApiProperties properties) {
		this.properties = properties;
	}

	@Bean
	public OpenAPI customOpenAPI(OpenApiProperties openApiProperties) {
		return new OpenAPI()
			.info(new Info()
				.title(openApiProperties.getInfo().title())
				.version(openApiProperties.getInfo().version())
				.description(openApiProperties.getInfo().description()))
			.addSecurityItem(new SecurityRequirement().addList(properties.getSecurity().scheme()))
			.components(new Components()
				.addSecuritySchemes(properties.getSecurity().scheme(),
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme(properties.getSecurity().schemeName())
						.description(properties.getSecurity().description())));
	}
}
