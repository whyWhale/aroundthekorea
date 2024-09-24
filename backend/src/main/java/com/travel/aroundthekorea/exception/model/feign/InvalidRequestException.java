package com.travel.aroundthekorea.exception.model.feign;

import com.travel.aroundthekorea.exception.model.CustomFeignException;
import com.travel.aroundthekorea.exception.model.ErrorMessage;

public class InvalidRequestException extends CustomFeignException {

	public InvalidRequestException(String message, ErrorMessage errorMessage) {
		super(message, errorMessage);
	}

	public InvalidRequestException(String message, Throwable cause, ErrorMessage errorMessage) {
		super(message, cause, errorMessage);
	}
}
