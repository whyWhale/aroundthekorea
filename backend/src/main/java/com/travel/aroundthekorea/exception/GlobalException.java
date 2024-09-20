package com.travel.aroundthekorea.exception;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.travel.aroundthekorea.exception.model.BatchException;
import com.travel.aroundthekorea.exception.model.ErrorMessage;

@RestControllerAdvice
public class GlobalException {

	private final Logger log = LoggerFactory.getLogger(GlobalException.class);

	@ExceptionHandler(BatchException.class)
	public ResponseEntity<ErrorResponse> handleBatchException(BatchException e) {
		ErrorMessage errorModel = e.getErrorModel();
		log.error("batch write exception : {}", errorModel.getServer());
		ErrorResponse response = ErrorResponse.builder(e, HttpStatus.SERVICE_UNAVAILABLE, errorModel.getClient())
			.type(URI.create("https://atk-monitor.com/errors/batch-error"))
			.title("[긴급] 관리자 호출")
			.detail(errorModel.getClient())
			.build();

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
