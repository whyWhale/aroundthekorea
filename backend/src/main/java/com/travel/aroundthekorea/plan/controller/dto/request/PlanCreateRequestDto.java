package com.travel.aroundthekorea.plan.controller.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record PlanCreateRequestDto(
	@NotNull
	String title,

	@NotNull
	@FutureOrPresent
	LocalDate startDate,

	@NotNull
	LocalDate endDate
) {
	public PlanCreateRequestDto {
		if(startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("Start date cannot be after end date");
		}
	}
}
