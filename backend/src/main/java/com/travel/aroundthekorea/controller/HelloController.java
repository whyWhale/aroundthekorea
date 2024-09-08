package com.travel.aroundthekorea.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

	@Value("${data.service-key}")
	private String serviceKey;

	@GetMapping("/hello")
	public String hello(@AuthenticationPrincipal User auth) {
		return auth.getUsername();
	}

	@GetMapping("/test")
	public String testgo() {
		return serviceKey;
	}
}