package com.travel.aroundthekorea.exception.model;

public enum ErrorMessage {
	BATCH_WRITE("관리자에게 문의해주세요.", "여행지 데이터 배치 작업 쓰기에서 예외가 발생했어요."),
	BATCH_RUNTIME("관리자에게 문의해주세요.", "여행지 데이터 배치 작업에 예상치 못한 문제가 발생했어요."),
	BATCH_CIRCUIT_BREAKER_FALLBACK("관리자에게 문의해주세요.", "여행지 데이터 서킷 브레이커 작업에서 까지 처리하지 못했어요."),
	BATCH_FEIGN_INVALID_REQUEST_HUMAN_ERROR("잘못된 요청이에요.", "잘못된 경로로 요청하였어요. 경로를 확인해주세요."),
	BATCH_OPEN_API_ERROR("관리자에게 문의해주세요.", "OPEN API 측에 문제가 있는 것 같아요. 해당 사이트에서 오류 코드를 비교해보세요."),
	BUSINESS_ERROR_NOT_HOST("잘못된 접근이에요.","다른 사람의 것을 열람하려고 시도하고 있어요." );

	private final String client;
	private final String server;

	ErrorMessage(String client, String server) {
		this.client = client;
		this.server = server;
	}

	public String getClient() {
		return client;
	}

	public String getServer() {
		return server;
	}
}
