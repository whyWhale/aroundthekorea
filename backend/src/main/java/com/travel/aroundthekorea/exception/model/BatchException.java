package com.travel.aroundthekorea.exception.model;

public class BatchException extends RuntimeException {
	private final ErrorMessage errorMessage;

	public BatchException(String message, ErrorMessage errorMessage) {
		super(message);
		this.errorMessage = errorMessage;
	}

	public BatchException(String message, Throwable cause, ErrorMessage errorMessage) {
		super(message, cause);
		this.errorMessage = errorMessage;
	}

	public ErrorMessage getErrorModel() {
		return errorMessage;
	}
}
