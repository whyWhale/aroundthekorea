package com.travel.aroundthekorea.exception.model.batch;

import com.travel.aroundthekorea.exception.model.BatchException;
import com.travel.aroundthekorea.exception.model.ErrorMessage;

public class SpotWriteException extends BatchException {
	public SpotWriteException(String message, ErrorMessage errorMessage) {
		super(message, errorMessage);
	}

	public SpotWriteException(String message, Throwable cause, ErrorMessage errorMessage) {
		super(message, cause, errorMessage);
	}
}
