package com.travel.aroundthekorea.plan.controller.dto.response;

import java.time.LocalDateTime;

public record SpotDetailResponse(
	Long id,
	String title,
	Integer contentId,
	String address,
	String latitude,
	String longitude,
	String image1,
	String image2,
	String category,
	LocalDateTime createdAt
) {
}
