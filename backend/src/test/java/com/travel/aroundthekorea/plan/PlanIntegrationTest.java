package com.travel.aroundthekorea.plan;

import static io.restassured.RestAssured.given;

import java.time.LocalDate;
import java.util.Base64;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import net.datafaker.Faker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.aroundthekorea.plan.controller.dto.request.PlanCreateRequestDto;
import com.travel.aroundthekorea.user.User;
import com.travel.aroundthekorea.user.UserRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanIntegrationTest {

	static final String PREFIX_URL = "/api/plans";

	static final Faker FAKER = new Faker();

	@LocalServerPort
	int port;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	User actor;

	String authorizationValue;

	@BeforeEach
	public void setup() {
		RestAssured.port = port;
		actor = userRepository.save(new User(
			"whyWhale",
			"aroundthekorea",
			FAKER.phoneNumber().phoneNumber()
		));
		authorizationValue = generateHeaderValue(actor);
	}

	@DisplayName("인증된 사용자가 계획표를 생성한다.")
	@Test
	public void testCreatePlan() throws JsonProcessingException {
		LocalDate startDate = LocalDate.now().plusDays(2);
		String body = objectMapper.writeValueAsString(new PlanCreateRequestDto(
			FAKER.book().title(),
			startDate,
			startDate.plusDays(3)
		));
		given()
			.header("Authorization", authorizationValue)
			.contentType(ContentType.JSON)
			.body(body)
			.when()
			.post(PREFIX_URL)
			.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.body(Matchers.equalTo("1"));
	}

	private static String generateHeaderValue(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		String toEncoding = username + ":" + password;
		return "BASIC " + Base64.getEncoder().encodeToString(toEncoding.getBytes());
	}
}