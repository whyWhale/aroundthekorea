package com.travel.aroundthekorea.config;

import java.time.Duration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travel.aroundthekorea.batch.spot.api.decoder.CustomErrorDecoder;
import com.travel.aroundthekorea.config.constant.FeignProperties;
import com.travel.aroundthekorea.exception.model.feign.InvalidRequestException;
import com.travel.aroundthekorea.exception.model.feign.OpenApiException;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@EnableFeignClients(basePackages = "com.travel.aroundthekorea.batch.spot.api")
@EnableConfigurationProperties(FeignProperties.class)
@Configuration
public class FeignConfig {
	private final FeignProperties feignProperties;

	public FeignConfig(FeignProperties feignProperties) {
		this.feignProperties = feignProperties;
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}

	@Bean
	public Feign.Builder feignBuilder() {
		Retryer.Default retryer = getPolicy();

		return Feign.builder()
			.retryer(retryer)
			.errorDecoder(errorDecoder());
	}

	@Bean
	public CircuitBreakerRegistry circuitBreakerRegistry() {
		CircuitBreakerConfig config = CircuitBreakerConfig.custom()
			.failureRateThreshold(50)
			.waitDurationInOpenState(Duration.ofSeconds(30))
			.ignoreExceptions(InvalidRequestException.class, OpenApiException.class)  // 무시할 예외 설정
			.build();
		return CircuitBreakerRegistry.of(config);
	}

	@Bean
	public Request.Options options() {
		return new Request.Options(feignProperties.connectTimeout(), feignProperties.readTimeout());
	}

	private Retryer.Default getPolicy() {
		return new Retryer.Default(feignProperties.retryer().period(),
			feignProperties.retryer().maxPeriod(),
			feignProperties.retryer().maxAttempts());
	}
}
