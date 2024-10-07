package com.travel.aroundthekorea.plan.controller.dto.response;

public record ReadySupplyResponse(
	Integer quantity,
	Boolean isReady,
	Long SupplyId,
	String word
) {
}
