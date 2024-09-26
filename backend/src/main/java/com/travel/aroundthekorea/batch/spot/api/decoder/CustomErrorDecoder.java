package com.travel.aroundthekorea.batch.spot.api.decoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.aroundthekorea.exception.model.ErrorMessage;
import com.travel.aroundthekorea.exception.model.feign.InvalidRequestException;
import com.travel.aroundthekorea.exception.model.feign.OpenApiException;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
	private static final Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);

	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() < 200) {
			log.error("open api error: {}", response.status());
			return new OpenApiException("open api error", ErrorMessage.BATCH_OPEN_API_ERROR);
		}

		FeignException exception = feign.FeignException.errorStatus(methodKey, response);

		return switch (response.status()) {
			case 404 -> new InvalidRequestException("", ErrorMessage.BATCH_FEIGN_INVALID_REQUEST_HUMAN_ERROR);
			case 500 -> new RetryableException(
				response.status(),
				exception.getMessage(),
				response.request().httpMethod(),
				exception,
				(Long)null,
				response.request()
			);

			default -> FeignException.errorStatus(methodKey, response);
		};
	}
}
