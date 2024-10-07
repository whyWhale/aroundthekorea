package com.travel.aroundthekorea.exception.model.business;

import com.travel.aroundthekorea.exception.model.BusinessException;
import com.travel.aroundthekorea.exception.model.ErrorMessage;

public class NotHostException extends BusinessException {
	public NotHostException(String message, ErrorMessage errorMessage) {
		super(message, errorMessage);
	}

	public NotHostException(String message, Throwable cause, ErrorMessage errorMessage) {
		super(message, cause, errorMessage);
	}
}
