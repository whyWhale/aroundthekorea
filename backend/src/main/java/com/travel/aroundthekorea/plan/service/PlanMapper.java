package com.travel.aroundthekorea.plan.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.travel.aroundthekorea.plan.controller.dto.response.CalenderDetailResponse;
import com.travel.aroundthekorea.plan.controller.dto.response.SpotDetailResponse;
import com.travel.aroundthekorea.plan.domain.Calender;
import com.travel.aroundthekorea.tour.domain.Spot;

@Component
public class PlanMapper {

	CalenderDetailResponse toCalenderDetailResponse(
		Map.Entry<Calender, List<SpotDetailResponse>> calenderDetail) {
		return new CalenderDetailResponse(
			calenderDetail.getKey().getId(),
			calenderDetail.getKey().getStartDate(),
			calenderDetail.getValue()
		);
	}

	SpotDetailResponse toSpotDetailResponse(Spot spot) {
		return new SpotDetailResponse(
			spot.getId(),
			spot.getTitle(),
			spot.getContentId(),
			spot.getAddress(),
			spot.getLatitude(),
			spot.getLongitude(),
			spot.getImage1(),
			spot.getImage2(),
			spot.getCategory(),
			spot.getCreatedAt()
		);
	}

}
