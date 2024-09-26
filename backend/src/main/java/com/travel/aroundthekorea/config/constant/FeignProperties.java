package com.travel.aroundthekorea.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import feign.Retryer;

@ConfigurationProperties(prefix = "feign.client.config.default")
public record FeignProperties(
	int connectTimeout,
	int readTimeout,
	Retryer retryer
) {
	@ConstructorBinding
	public FeignProperties {
	}

	public record Retryer(
		int period,
		int maxPeriod,
		int maxAttempts
	) {

	}
}
