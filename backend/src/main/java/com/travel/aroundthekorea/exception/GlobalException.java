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
import com.travel.aroundthekorea.exception.model.CustomFeignException;
import com.travel.aroundthekorea.exception.model.ErrorMessage;

import feign.FeignException;
import feign.RetryableException;

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

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {
		if (e instanceof RetryableException) {
			log.error("Feign Retryable exception: {}", e.getMessage());
			ErrorResponse response = ErrorResponse.builder(e, HttpStatus.GATEWAY_TIMEOUT, "Retry Error")
				.type(URI.create("https://atk-monitor.com/errors/retry-error"))
				.title("재시도 실패")
				.detail(e.getMessage())
				.build();

			return new ResponseEntity<>(response, HttpStatus.GATEWAY_TIMEOUT);
		}

		log.error("Feign exception occurred: {}", e.getMessage());
		ErrorResponse response = ErrorResponse.builder(e, HttpStatus.SERVICE_UNAVAILABLE, "Feign API Error")
			.type(URI.create("https://atk-monitor.com/errors/feign-error"))
			.title("Feign API 호출 오류")
			.detail(e.getMessage())
			.build();

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(CustomFeignException.class)
	public ResponseEntity<ErrorResponse> handleFeignException(CustomFeignException e) {
		ErrorMessage errorModel = e.getErrorModel();
		log.error("Feign Decode exception occurred: {}, raw message: {}", errorModel.getServer(), e.getMessage());
		ErrorResponse response = ErrorResponse.builder(e, HttpStatus.SERVICE_UNAVAILABLE, "Feign API Error")
			.type(URI.create("https://atk-monitor.com/errors/feign-error"))
			.title(e.getErrorModel().getClient())
			.detail(e.getMessage())
			.build();

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
