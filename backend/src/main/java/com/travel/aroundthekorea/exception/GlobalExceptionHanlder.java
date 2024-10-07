package com.travel.aroundthekorea.exception;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.travel.aroundthekorea.exception.model.BatchException;
import com.travel.aroundthekorea.exception.model.BusinessException;
import com.travel.aroundthekorea.exception.model.CustomFeignException;
import com.travel.aroundthekorea.exception.model.ErrorMessage;

import feign.FeignException;
import feign.RetryableException;

@RestControllerAdvice
public class GlobalExceptionHanlder {

	private final Logger log = LoggerFactory.getLogger(GlobalExceptionHanlder.class);

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
		ErrorMessage errorModel = exception.getErrorModel();
		log.error("business exception : {}", errorModel.getServer(), exception);
		ErrorResponse response = ErrorResponse.builder(exception, HttpStatus.NOT_ACCEPTABLE, errorModel.getClient())
			.detail(errorModel.getClient())
			.build();

		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(BatchException.class)
	public ResponseEntity<ErrorResponse> handleBatchException(BatchException exception) {
		ErrorMessage errorModel = exception.getErrorModel();
		log.error("batch write exception : {}", errorModel.getServer(), exception);
		ErrorResponse response = ErrorResponse.builder(exception, HttpStatus.SERVICE_UNAVAILABLE, errorModel.getClient())
			.type(URI.create("https://atk-monitor.com/errors/batch-error"))
			.title("[긴급] 관리자 호출")
			.detail(errorModel.getClient())
			.build();

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ErrorResponse> handleFeignException(FeignException exception) {
		if (exception instanceof RetryableException) {
			log.error("Feign Retryable exception: {}", exception.getMessage(), exception);
			ErrorResponse response = ErrorResponse.builder(exception, HttpStatus.GATEWAY_TIMEOUT, "Retry Error")
				.type(URI.create("https://atk-monitor.com/errors/retry-error"))
				.title("재시도 실패")
				.detail(exception.getMessage())
				.build();

			return new ResponseEntity<>(response, HttpStatus.GATEWAY_TIMEOUT);
		}

		log.error("Feign exception occurred: {}", exception.getMessage(), exception);
		ErrorResponse response = ErrorResponse.builder(exception, HttpStatus.SERVICE_UNAVAILABLE, "Feign API Error")
			.type(URI.create("https://atk-monitor.com/errors/feign-error"))
			.title("Feign API 호출 오류")
			.detail(exception.getMessage())
			.build();

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(CustomFeignException.class)
	public ResponseEntity<ErrorResponse> handleFeignException(CustomFeignException exception) {
		ErrorMessage errorModel = exception.getErrorModel();
		log.error("Feign Decode exception occurred: {}, raw message: {}", errorModel.getServer(), exception.getMessage(), exception);
		ErrorResponse response = ErrorResponse.builder(exception, HttpStatus.SERVICE_UNAVAILABLE, "Feign API Error")
			.type(URI.create("https://atk-monitor.com/errors/feign-error"))
			.title(exception.getErrorModel().getClient())
			.detail(exception.getMessage())
			.build();

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		log.error("validation exception occurred: {}", ex.getBindingResult().getAllErrors().stream().toList());
		Map<String, String> errors = ex.getFieldErrors()
			.stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				DefaultMessageSourceResolvable::getDefaultMessage
			));

		return ResponseEntity.badRequest().body(errors);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ResponseEntity<Map<String, String>> handleBindException(BindException exception) {
		Map<String, String> errors = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				DefaultMessageSourceResolvable::getDefaultMessage
			));

		return ResponseEntity.badRequest().body(errors);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
		log.error("IllegalArgumentException occurred: {}", exception.getMessage(), exception);
		return ResponseEntity.badRequest()
			.contentType(MediaType.APPLICATION_JSON)
			.body(exception.getMessage());
	}
}
