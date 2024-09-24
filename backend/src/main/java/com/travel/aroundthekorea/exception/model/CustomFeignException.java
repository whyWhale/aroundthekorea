package com.travel.aroundthekorea.exception.model;

public class CustomFeignException extends RuntimeException {
	private final ErrorMessage errorMessage;

	public CustomFeignException(String message, ErrorMessage errorMessage) {
		super(message);
		this.errorMessage = errorMessage;
	}

	public CustomFeignException(String message, Throwable cause, ErrorMessage errorMessage) {
		super(message, cause);
		this.errorMessage = errorMessage;
	}

	public ErrorMessage getErrorModel() {
		return errorMessage;
	}
}
