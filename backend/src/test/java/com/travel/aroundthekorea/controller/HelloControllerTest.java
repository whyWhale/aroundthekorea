package com.travel.aroundthekorea.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.travel.aroundthekorea.config.SecurityConfig;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("등록한 사용자를 헤더정보에 담으면 접근할 수 있다.")
	void testHello() throws Exception {
		//given
		String username = "whyWhale";
		String password = "aroundthekorea";
		String toEncoding = username + ":" + password;
		String encodedUser = Base64.getEncoder()
			.encodeToString(toEncoding.getBytes());
		String authorizationValue = "Basic " + encodedUser;
		//when
		ResultActions perform = mockMvc.perform(
			get("/hello")
				// .header("Authorization", AUTHORIZATION_VALUE)
				.header("Authorization", authorizationValue)
		);
		//then
		perform.andExpect(status().isOk())
			.andDo(log());
		String body = perform.andReturn().getResponse().getContentAsString();
		assertThat(body).isEqualTo(username);
	}

	@Test
	@DisplayName("등록한 사용자에 대한 헤더 정보가 없으면 접근할 수 없다")
	void failHello() throws Exception {
		//given
		//when
		ResultActions perform = mockMvc.perform(get("/hello"));
		//then
		perform.andExpect(status().isUnauthorized())
			.andDo(log());
	}

}