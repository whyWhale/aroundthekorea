package com.travel.aroundthekorea.exception.model;

public class BusinessException extends RuntimeException {
	private final ErrorMessage errorMessage;

	public BusinessException(String message, ErrorMessage errorMessage) {
		super(message);
		this.errorMessage = errorMessage;
	}

	public BusinessException(String message, Throwable cause, ErrorMessage errorMessage) {
		super(message, cause);
		this.errorMessage = errorMessage;
	}

	public ErrorMessage getErrorModel() {
		return errorMessage;
	}
}
