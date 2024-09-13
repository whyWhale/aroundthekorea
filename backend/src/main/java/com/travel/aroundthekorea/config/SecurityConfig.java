package com.travel.aroundthekorea.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class SecurityConfig {

	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

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

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@SuppressWarnings("deprecation")
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.httpBasic(Customizer.withDefaults())
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/test").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(form -> form.disable())
			.logout(logout -> logout.disable())
			.sessionManagement(session -> session
				.sessionFixation().migrateSession()
			)
			.build();
	}
}
