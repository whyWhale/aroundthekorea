package com.travel.aroundthekorea.exception.model.feign;

import com.travel.aroundthekorea.exception.model.CustomFeignException;
import com.travel.aroundthekorea.exception.model.ErrorMessage;

public class OpenApiException extends CustomFeignException {
	public OpenApiException(String message, Throwable cause, ErrorMessage errorMessage) {
		super(message, cause, errorMessage);
	}

	public OpenApiException(String message, ErrorMessage errorMessage) {
		super(message, errorMessage);
	}
}
