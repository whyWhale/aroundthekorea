package com.travel.aroundthekorea.config.constant;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "spring.cors")
public record CorsProperties(List<String> allowOrigins) {

	@ConstructorBinding
	public CorsProperties {
	}
}
