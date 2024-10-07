package com.travel.aroundthekorea.plan.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import net.datafaker.Faker;

import com.travel.aroundthekorea.plan.controller.dto.request.PlanCreateRequestDto;
import com.travel.aroundthekorea.plan.controller.dto.response.PlanDetailResponse;
import com.travel.aroundthekorea.plan.domain.Calender;
import com.travel.aroundthekorea.plan.domain.Plan;
import com.travel.aroundthekorea.plan.repository.CalenderRepository;
import com.travel.aroundthekorea.plan.repository.PlanRepository;
import com.travel.aroundthekorea.plan.service.PlanService;
import com.travel.aroundthekorea.user.User;
import com.travel.aroundthekorea.user.UserRepository;

@Transactional
@SpringBootTest
class PlanIntegrationServiceTest {
	@Autowired
	PlanService planService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CalenderRepository calenderRepository;

	User authUser;

	@Autowired
	private PlanRepository planRepository;

	Faker faker = new Faker();

	@BeforeEach
	public void preProcess() {
		authUser = userRepository.save(new User(
			"kaggle@gmail.com",
			"{encrypt} phyphy",
			"010-1193-0202"
		));
		assertThat(authUser).isNotNull();
		userRepository.flush();
	}

	@AfterEach
	public void postProcess() {
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("계획표 생성 후 시작날짜부터 끝나는 날까지 calender 들도 만들어준다.")
	void testCreatePlan() {
		//given
		PlanCreateRequestDto planRequestDto = new PlanCreateRequestDto(
			"떠나요 쥬쥬도",
			LocalDate.now(),
			LocalDate.now().plusDays(10)
		);
		//when
		Long planId = planService.create(authUser.getUsername(), planRequestDto);
		//then

		Plan plan = planRepository.findById(planId).orElseThrow();
		assertThat(plan.getStartDate()).isEqualTo(planRequestDto.startDate());
		assertThat(plan.getEndDate()).isEqualTo(planRequestDto.endDate());
		assertThat(plan.getCompleted()).isFalse();

		Set<LocalDate> dateContainer = planRequestDto.startDate().datesUntil(planRequestDto.endDate().plusDays(1))
			.collect(Collectors.toSet());
		calenderRepository.findByPlan(plan).stream().map(Calender::getStartDate)
			.forEach(startDate -> assertThat(startDate).isIn(dateContainer));
	}

	@Test
	@DisplayName("계획표를 조회한다")
	void testGetPlan() {
		//given
		LocalDate dDayStart = LocalDate.now();
		LocalDate dDayEnd = LocalDate.now().plusDays(10);
		PlanCreateRequestDto planRequestDto = new PlanCreateRequestDto(
			"떠나요 쥬쥬도",
			dDayStart,
			dDayEnd
		);
		Long planId = planService.create(authUser.getUsername(), planRequestDto);

		//when
		PlanDetailResponse detailResponse = planService.getPlan(authUser.getUsername(), planId);
		
		//then
		assertThat(detailResponse).isNotNull();
		assertThat(detailResponse.planId()).isEqualTo(planId);
		assertThat(detailResponse.planTitle()).isEqualTo(planRequestDto.title());
		assertThat(detailResponse.startDate()).isEqualTo(planRequestDto.startDate());
		assertThat(detailResponse.endDate()).isEqualTo(planRequestDto.endDate());
		assertThat(detailResponse.isCompleted()).isFalse();
		assertThat(detailResponse.calenderDetailResponses()).hasSize(dDayEnd.compareTo(dDayStart)+1);
		assertThat(detailResponse.readySupplyResponses()).isEmpty();
	}

}