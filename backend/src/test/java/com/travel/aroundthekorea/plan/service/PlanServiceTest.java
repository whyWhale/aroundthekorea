package com.travel.aroundthekorea.plan.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.travel.aroundthekorea.plan.controller.dto.request.PlanCreateRequestDto;
import com.travel.aroundthekorea.plan.domain.Calender;
import com.travel.aroundthekorea.plan.domain.Plan;
import com.travel.aroundthekorea.plan.repository.CalenderRepository;
import com.travel.aroundthekorea.plan.repository.PlanRepository;
import com.travel.aroundthekorea.user.User;
import com.travel.aroundthekorea.user.UserRepository;

import jakarta.persistence.EntityManager;

@Transactional
@SpringBootTest
class PlanServiceTest {
	@Autowired
	PlanService planService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CalenderRepository calenderRepository;

	User authUser;

	@Autowired
	private PlanRepository planRepository;


	@BeforeEach
	public void preProcess() {
		authUser = userRepository.save(new User(
			"kaggle@gmail.com",
			"{encrypt} phyphy",
			"010-1193-0202"
		));
		userRepository.flush();
		assertThat(authUser).isNotNull();
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

}