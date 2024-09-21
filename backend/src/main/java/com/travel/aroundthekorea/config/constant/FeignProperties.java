package com.travel.aroundthekorea.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "feign.client.config.default")
public record FeignProperties(
	int connectTimeout,
	int readTimeout,
	int period,
	int maxPeriod,
	int maxAttempts
) {
	@ConstructorBinding
	public FeignProperties {
	}
}
