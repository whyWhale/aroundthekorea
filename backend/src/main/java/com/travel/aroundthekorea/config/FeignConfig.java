package com.travel.aroundthekorea.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travel.aroundthekorea.config.constant.FeignProperties;

import feign.Feign;
import feign.Request;
import feign.Retryer;

@EnableFeignClients(basePackages = "com.travel.aroundthekorea.batch.spot.api")
@EnableConfigurationProperties(FeignProperties.class)
@Configuration
public class FeignConfig {
	private final FeignProperties feignProperties;

	public FeignConfig(FeignProperties feignProperties) {
		this.feignProperties = feignProperties;
	}

	@Bean
	public Feign.Builder feignBuilder() {
		Retryer.Default retryer = getPolicy();

		return Feign.builder()
			.retryer(retryer);
	}

	@Bean
	public Request.Options options() {
		return new Request.Options(feignProperties.connectTimeout(), feignProperties.readTimeout());
	}

	private Retryer.Default getPolicy() {
		return new Retryer.Default(feignProperties.period(),
			feignProperties.maxPeriod(),
			feignProperties.maxAttempts());
	}
}
