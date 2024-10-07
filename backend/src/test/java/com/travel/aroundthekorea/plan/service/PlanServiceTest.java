package com.travel.aroundthekorea.plan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.datafaker.Faker;

import com.travel.aroundthekorea.exception.model.BusinessException;
import com.travel.aroundthekorea.plan.domain.Plan;
import com.travel.aroundthekorea.plan.repository.CalenderRepository;
import com.travel.aroundthekorea.plan.repository.PlanRepository;
import com.travel.aroundthekorea.plan.repository.ReadySupplyRepository;
import com.travel.aroundthekorea.plan.repository.TravelCalenderRepository;
import com.travel.aroundthekorea.user.User;
import com.travel.aroundthekorea.user.UserRepository;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {
	@InjectMocks
	PlanService planService;

	@Mock
	UserRepository userRepository;

	@Mock
	PlanRepository planRepository;

	@Mock
	CalenderRepository calenderRepository;

	@Mock
	TravelCalenderRepository travelCalenderRepository;

	@Mock
	ReadySupplyRepository readySupplyRepository;

	@Mock
	PlanMapper planMapper;

	Faker faker = new Faker();

	String AUTH_USERNAME = "kaggle@gmail.com";

	@Test
	@DisplayName("자신의 것이 아닌 계획표를 조회하면 예외가 발생한다")
	void failGetPlan() {
		//given
		LocalDate today = LocalDate.now();
		long anotherUserId = 1L;
		long invalidPlanId = 2L;
		Plan expectedPlan = new Plan(anotherUserId, today, today.plusDays(3), faker.book().title(), false);
		given(userRepository.findByUsername(AUTH_USERNAME)).willReturn(Optional.of(new User(AUTH_USERNAME, "", "")));
		given(planRepository.findById(invalidPlanId)).willReturn(Optional.of(expectedPlan));

		//when
		//then
		Assertions.assertThatThrownBy(() -> {
			planService.getPlan(AUTH_USERNAME, 2L);
		}).isInstanceOf(BusinessException.class);
	}

}