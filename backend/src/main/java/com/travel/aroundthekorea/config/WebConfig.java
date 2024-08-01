package com.travel.aroundthekorea.config;

import java.util.Arrays;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.travel.aroundthekorea.config.constant.CorsProperties;

@Profile({"local", "prod"})
@EnableConfigurationProperties(CorsProperties.class)
@Configuration
public class WebConfig implements WebMvcConfigurer {
	private  CorsProperties corsProperties;

	public WebConfig(CorsProperties corsProperties) {
		this.corsProperties = corsProperties;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(corsProperties.allowOrigins().toArray(String[]::new))
			.allowedMethods(getMethod())
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(3600);
	}

	private static String[] getMethod() {
		return Arrays.stream(HttpMethod.values())
			.map(HttpMethod::name)
			.toArray(String[]::new);
	}
}

