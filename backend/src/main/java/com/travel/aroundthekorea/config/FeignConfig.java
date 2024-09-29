package com.travel.aroundthekorea.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travel.aroundthekorea.batch.spot.api.decoder.CustomErrorDecoder;
import com.travel.aroundthekorea.config.constant.FeignProperties;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;

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
	public Retryer.Default retryer() {
		return new Retryer.Default(feignProperties.retryer().period(),
			feignProperties.retryer().maxPeriod(),
			feignProperties.retryer().maxAttempts());
	}

	@Bean
	public Feign.Builder feignBuilder(Retryer retryer, ErrorDecoder errorDecoder) {
		return Feign.builder()
			.retryer(retryer)
			.errorDecoder(errorDecoder);
	}

	@Bean
	public Request.Options options() {
		return new Request.Options(
			feignProperties.connectTimeout(),
			feignProperties.readTimeout());
	}
}
