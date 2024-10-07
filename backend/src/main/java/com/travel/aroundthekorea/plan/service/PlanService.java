package com.travel.aroundthekorea.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.aroundthekorea.exception.model.ErrorMessage;
import com.travel.aroundthekorea.exception.model.business.NotHostException;
import com.travel.aroundthekorea.plan.controller.dto.request.PlanCreateRequestDto;
import com.travel.aroundthekorea.plan.controller.dto.response.PlanDetailResponse;
import com.travel.aroundthekorea.plan.controller.dto.response.ReadySupplyResponse;
import com.travel.aroundthekorea.plan.controller.dto.response.SpotDetailResponse;
import com.travel.aroundthekorea.plan.domain.Calender;
import com.travel.aroundthekorea.plan.domain.Plan;
import com.travel.aroundthekorea.plan.domain.ReadySupplies;
import com.travel.aroundthekorea.plan.domain.TravelCalender;
import com.travel.aroundthekorea.plan.repository.CalenderRepository;
import com.travel.aroundthekorea.plan.repository.PlanRepository;
import com.travel.aroundthekorea.plan.repository.ReadySupplyRepository;
import com.travel.aroundthekorea.plan.repository.TravelCalenderRepository;
import com.travel.aroundthekorea.user.User;
import com.travel.aroundthekorea.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Transactional(readOnly = true)
@Service
public class PlanService {
	private final UserRepository userRepository;
	private final PlanRepository planRepository;
	private final CalenderRepository calenderRepository;
	private final TravelCalenderRepository travelCalenderRepository;
	private final ReadySupplyRepository readySupplyRepository;
	private final PlanMapper planMapper;

	public PlanService(UserRepository userRepository, PlanRepository planRepository,
		CalenderRepository calenderRepository, TravelCalenderRepository travelCalenderRepository,
		ReadySupplyRepository readySupplyRepository, PlanMapper planMapper) {
		this.userRepository = userRepository;
		this.planRepository = planRepository;
		this.calenderRepository = calenderRepository;
		this.travelCalenderRepository = travelCalenderRepository;
		this.readySupplyRepository = readySupplyRepository;
		this.planMapper = planMapper;
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

	public PlanDetailResponse getPlan(String username, Long planId) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new EntityNotFoundException("not authentication"));
		Plan plan = planRepository.findById(planId)
			.orElseThrow(() -> new EntityNotFoundException("not found resources."));

		boolean hasNot = !plan.isHost(user.getId());
		if (hasNot) {
			throw new NotHostException("not host plan", ErrorMessage.BUSINESS_ERROR_NOT_HOST);
		}

		List<ReadySupplies> readySupplies = readySupplyRepository.findByPlanWithSupply(plan);
		List<Calender> calenders = calenderRepository.findByPlan(plan);
		List<TravelCalender> travelCalenders = travelCalenderRepository.findByCalendarInWithSpots(calenders);
		Map<Calender, List<TravelCalender>> calenderToTravelCalendersMap = travelCalenders.stream()
			.collect(Collectors.groupingBy(TravelCalender::getCalendar));
		Map<Calender, List<SpotDetailResponse>> calenderToSpotDetailsMap = calenderToTravelCalendersMap.entrySet()
			.stream()
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				entry -> entry.getValue().stream()
					.map(TravelCalender::getSpot)
					.map(planMapper::toSpotDetailResponse).toList()
			));
		calenders.forEach(calender -> calenderToSpotDetailsMap.putIfAbsent(calender, new ArrayList<>()));

		return new PlanDetailResponse(
			plan.getId(),
			plan.getTitle(),
			plan.getStartDate(),
			plan.getEndDate(),
			plan.getCompleted(),
			calenderToSpotDetailsMap.entrySet().stream()
				.map(planMapper::toCalenderDetailResponse)
				.toList(),
			readySupplies.stream()
				.map(v -> new ReadySupplyResponse(
						v.getQuantity(),
						v.getReady(),
						v.getSupply().getId(),
						v.getSupply().getWord()
					)
				).toList()
		);
	}
}
