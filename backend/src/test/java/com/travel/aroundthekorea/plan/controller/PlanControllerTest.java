package com.travel.aroundthekorea.plan.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import net.datafaker.Faker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.aroundthekorea.config.SecurityConfig;
import com.travel.aroundthekorea.plan.controller.dto.request.PlanCreateRequestDto;
import com.travel.aroundthekorea.plan.service.PlanService;

@WithMockUser(username = "guest")
@WebMvcTest({PlanController.class, SecurityConfig.class})
class PlanControllerTest {
	final static String PREFIX_URL = "/api/plans";
	final static String MOCK_USER_NAME = "guest";

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PlanService planService;

	Faker faker = new Faker();

	@Test
	@WithMockUser(username = MOCK_USER_NAME)
	@DisplayName("여행계획을 세우다")
	void testCreatePlan() throws Exception {
		//given
		LocalDate startDate = LocalDate.now().plusDays(3);
		PlanCreateRequestDto requestDto1 = new PlanCreateRequestDto(
			faker.book().title(),
			startDate,
			startDate.plusDays(4)
		);
		PlanCreateRequestDto requestDto = requestDto1;
		long expectedPlanId = 1L;
		given(planService.create(MOCK_USER_NAME, requestDto)).willReturn(expectedPlanId);
		//when
		ResultActions perform = getPerform(requestDto);

		//then
		perform.andExpect(status().isOk());
		String body = perform.andReturn()
			.getResponse()
			.getContentAsString();
		Long planId = objectMapper.readValue(body, Long.class);
		Assertions.assertThat(planId).isEqualTo(expectedPlanId);
		verify(planService, times(1)).create(MOCK_USER_NAME, requestDto);
	}

	@DisplayName("여행계획을 생성하려고 할때, ")
	@Nested
	class ValidationTest {
		@Test
		@DisplayName("시작일자가 과거일자이면 예외가 발생한다.")
		void failPastDateTimeAtStartDate() throws Exception {
			// given
			LocalDate now = LocalDate.now();
			PlanCreateRequestDto planCreateRequestDto = getPlanCreateRequestDto(now.minusDays(10), now.plusDays(2));
			//when
			//then
			getPerform(planCreateRequestDto)
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().string(containsString("present")))
				.andExpect(content().string(containsString("future")));
		}

		@Test
		@DisplayName("시작일자와 과거일자가 교차하면 예외가 발생한다.")
		void testNotCrossDateTime() throws Exception {
			// given
			LocalDate now = LocalDate.now();
			System.out.println("now = " + now);
			//when
			//then
			String jsonContent = String.format("{ \"title\": \"Amazing Travel Plan\", \"startDate\": \"%s\", \"endDate\": \"%s\" }", now.plusDays(10),now.plusDays(5));
			mockMvc.perform(post(PREFIX_URL)
					.contentType(APPLICATION_JSON)
					.content(jsonContent))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().string(containsString("Start date cannot be after end date")));
		}

		private PlanCreateRequestDto getPlanCreateRequestDto(LocalDate startDate, LocalDate endDate) {
			return new PlanCreateRequestDto(
				faker.book().title(),
				startDate,
				endDate
			);
		}

	}

	private ResultActions getPerform(PlanCreateRequestDto requestDto) throws Exception {
		return mockMvc.perform(post(PREFIX_URL)
			.contentType(APPLICATION_JSON)
			.content(objectMapper.writeValueAsBytes(requestDto)));
	}
}