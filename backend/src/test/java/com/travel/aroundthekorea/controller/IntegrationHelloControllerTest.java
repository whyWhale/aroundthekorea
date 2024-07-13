package com.travel.aroundthekorea.controller;

import static io.restassured.RestAssured.*;

import java.util.Base64;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.http.Header;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationHelloControllerTest {
	@LocalServerPort
	int port;

	@Test
	@DisplayName("등록한 사용자를 헤더정보에 담으면 접근할 수 있다.")
	void testHello() {
		//given
		String encodedUser = Base64.getEncoder()
			.encodeToString("whyWhale:aroundthekorea".getBytes());
		String authorizationValue = "Basic " + encodedUser;
		//when
		//then
		Header authorizationHeader = new Header("Authorization", authorizationValue);
		given().port(port).header(authorizationHeader)
			.when().get("/hello")
			.then().log().all()
			.statusCode(HttpStatus.OK.value())
			.assertThat()
			.body(Matchers.is("hello world"));
	}

	@Test
	@DisplayName("등록한 사용자에 대한 헤더 정보가 없으면 접근할 수 없다")
	void failHello(){
		//given
		//when
		//then
		given().port(port)
			.when().get("/hello")
			.then().log().all()
			.statusCode(HttpStatus.UNAUTHORIZED.value());
	}
}
