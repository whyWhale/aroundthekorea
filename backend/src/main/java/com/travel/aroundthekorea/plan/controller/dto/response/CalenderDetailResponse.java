package com.travel.aroundthekorea.plan.controller.dto.response;

import java.time.LocalDate;
import java.util.List;

public record CalenderDetailResponse(
	Long calenderId,
	LocalDate date,
	List<SpotDetailResponse> spotDetailResponses
) {
}
