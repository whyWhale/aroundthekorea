package com.travel.aroundthekorea.plan.integration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Base64;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
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
import com.travel.aroundthekorea.plan.controller.dto.response.PlanDetailResponse;
import com.travel.aroundthekorea.plan.repository.CalenderRepository;
import com.travel.aroundthekorea.plan.service.PlanService;
import com.travel.aroundthekorea.user.User;
import com.travel.aroundthekorea.user.UserRepository;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import com.fasterxml.jackson.core.type.TypeReference;

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

	@Autowired
	PlanService planService;

	@Autowired
	CalenderRepository calenderRepository;

	User actor;

	String authorizationValue;
	final User AUTH_USER = new User(
		"whyWhale",
		"aroundthekorea",
		FAKER.phoneNumber().phoneNumber()
	);

	@BeforeEach
	public void preProcess() {
		RestAssured.port = port;
		actor = userRepository.save(AUTH_USER);
		authorizationValue = generateHeaderValue(actor);
		RestAssured.filters(
			new RequestLoggingFilter(LogDetail.ALL),
			new ResponseLoggingFilter(LogDetail.ALL)
		);
	}

	@AfterEach
	public void postProcess() {
		userRepository.deleteAllInBatch();
	}

	@DisplayName("인증된 사용자가 계획표를 생성한다.")
	@Test
	public void testCreate() throws JsonProcessingException {
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
			.body(Matchers.notNullValue());
	}

	@DisplayName("인증된 사용자가 자신의 계획표를 조회한다.")
	@Test
	public void testRead() throws JsonProcessingException {
		LocalDate startDate = LocalDate.now().plusDays(2);
		PlanCreateRequestDto requestDto = new PlanCreateRequestDto(
			FAKER.book().title(),
			startDate,
			startDate.plusDays(3)
		);
		Long myPlanId = planService.create(AUTH_USER.getUsername(), requestDto);
		String path = "/" + myPlanId;
		PlanDetailResponse detailResponse = given()
			.header("Authorization", authorizationValue)
			.contentType(ContentType.JSON)
			.when()
			.get(PREFIX_URL + path)
			.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(PlanDetailResponse.class);

		assertThat(detailResponse).isNotNull();
		assertThat(detailResponse.planId()).isEqualTo(myPlanId);
		assertThat(detailResponse.planTitle()).isEqualTo(requestDto.title());
		assertThat(detailResponse.startDate()).isEqualTo(requestDto.startDate());
		assertThat(detailResponse.endDate()).isEqualTo(requestDto.endDate());
		int expectedDateCount = requestDto.endDate().compareTo(requestDto.startDate())+1;
		assertThat(detailResponse.calenderDetailResponses()).hasSize(expectedDateCount);
		assertThat(detailResponse.readySupplyResponses()).isEmpty();
	}

	private static String generateHeaderValue(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		String toEncoding = username + ":" + password;
		return "BASIC " + Base64.getEncoder().encodeToString(toEncoding.getBytes());
	}
}