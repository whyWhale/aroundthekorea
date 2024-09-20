package com.travel.aroundthekorea.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.travel.aroundthekorea.batch.spot.api")
@Configuration
public class FeignConfig {

}
