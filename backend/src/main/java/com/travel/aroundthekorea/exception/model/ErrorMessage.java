package com.travel.aroundthekorea.exception.model;

public enum ErrorMessage {
	BATCH_WRITE("관리자에게 문의해주세요.", "여행지 데이터 배치 작업 쓰기에서 예외가 발생했어요."),
	BATCH_RUNTIME("관리자에게 문의해주세요.", "여행지 데이터 배치 작업에 예상치 못한 문제가 발생했어요.");

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
