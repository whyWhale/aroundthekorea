package com.travel.aroundthekorea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	UserDetailsService userDetailsService() {
		var demoUser = User.withUsername("whyWhale")
			.password("aroundthekorea")
			.authorities("ROLE_USER")
			.accountExpired(false)
			.accountLocked(false)
			.build();

		return new InMemoryUserDetailsManager(demoUser);
	}

	@SuppressWarnings("deprecation")
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http.httpBasic(Customizer.withDefaults())
			.authorizeHttpRequests(c -> c.anyRequest().authenticated())
			.build();
	}
}
