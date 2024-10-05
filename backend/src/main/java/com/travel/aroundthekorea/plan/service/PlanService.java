package com.travel.aroundthekorea.plan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.aroundthekorea.plan.controller.dto.request.PlanCreateRequestDto;
import com.travel.aroundthekorea.plan.domain.Calender;
import com.travel.aroundthekorea.plan.domain.Plan;
import com.travel.aroundthekorea.plan.repository.CalenderRepository;
import com.travel.aroundthekorea.plan.repository.PlanRepository;
import com.travel.aroundthekorea.user.User;
import com.travel.aroundthekorea.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Transactional(readOnly = true)
@Service
public class PlanService {
	private final UserRepository userRepository;
	private final PlanRepository planRepository;
	private final CalenderRepository calenderRepository;

	public PlanService(UserRepository userRepository, PlanRepository planRepository,
		CalenderRepository calenderRepository) {
		this.userRepository = userRepository;
		this.planRepository = planRepository;
		this.calenderRepository = calenderRepository;
	}

	@Transactional
	public Long create(String username, PlanCreateRequestDto requestDto) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new EntityNotFoundException("not found resources."));
		Plan savedPlan = planRepository.save(new Plan(
			user.getId(),
			requestDto.startDate(),
			requestDto.endDate(),
			requestDto.title(),
			false
		));

		List<Calender> calenders = savedPlan.getStartDate().datesUntil(savedPlan.getEndDate().plusDays(1))
			.map(startDate -> new Calender(savedPlan, startDate))
			.toList();
		calenderRepository.saveAll(calenders);

		return savedPlan.getId();
	}
}
