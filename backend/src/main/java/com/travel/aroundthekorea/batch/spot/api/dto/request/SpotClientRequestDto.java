package com.travel.aroundthekorea.batch.spot.api.dto.request;

public record SpotClientRequestDto(
	int numOfRows,
	int pageNo,
	String mobileOS,
	String mobileApp,
	String type,
	String serviceKey,
	String order
) {
}
