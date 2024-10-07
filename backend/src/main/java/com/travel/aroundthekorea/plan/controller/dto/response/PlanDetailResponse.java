package com.travel.aroundthekorea.plan.controller.dto.response;

import java.time.LocalDate;
import java.util.List;

public record PlanDetailResponse(
	Long planId,
	String planTitle,
	LocalDate startDate,
	LocalDate endDate,
	Boolean isCompleted,
	List<CalenderDetailResponse> calenderDetailResponses,
	List<ReadySupplyResponse> readySupplyResponses
) {
}
