package com.travel.aroundthekorea.plan.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.aroundthekorea.plan.controller.dto.request.PlanCreateRequestDto;
import com.travel.aroundthekorea.plan.controller.dto.response.PlanDetailResponse;
import com.travel.aroundthekorea.plan.domain.Plan;
import com.travel.aroundthekorea.plan.service.PlanService;

import jakarta.validation.Valid;

@RequestMapping("/api/plans")
@RestController
public class PlanController {

	private final PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	@PostMapping
	public Long create(@AuthenticationPrincipal User auth,
		@Valid @RequestBody PlanCreateRequestDto requestDto) {
		return planService.create(auth.getUsername(), requestDto);
	}

	@GetMapping("/{plan_id}")
	public PlanDetailResponse read(@AuthenticationPrincipal User auth,
		@PathVariable(name = "plan_id") Long planId) {
		return planService.getPlan(auth.getUsername(),planId);
	}

	@PatchMapping("/{plan_id}")
	public Map<String, String> modify(
		@AuthenticationPrincipal Authentication auth,
		@RequestBody Plan plan,
		@PathVariable(name = "plan_id") Long planId) {
		return Map.of();
	}

	@DeleteMapping("/{plan_id}")
	public Map<String, String> remove(@PathVariable(name = "plan_id") Long planId) {
		return Map.of();
	}
}
