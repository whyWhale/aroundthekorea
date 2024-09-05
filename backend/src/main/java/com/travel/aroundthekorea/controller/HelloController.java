package com.travel.aroundthekorea.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

	@Value("${data.service-key}")
	private String serviceKey;

	@GetMapping("/hello")
	public String hello() {
		return "hello world";
	}

	@GetMapping("/test")
	public String testgo() {
		return serviceKey;
	}
}