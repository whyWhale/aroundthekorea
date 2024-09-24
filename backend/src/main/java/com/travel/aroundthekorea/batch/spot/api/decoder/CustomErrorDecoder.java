package com.travel.aroundthekorea.batch.spot.api.decoder;

import com.travel.aroundthekorea.exception.model.ErrorMessage;
import com.travel.aroundthekorea.exception.model.feign.InvalidRequestException;
import com.travel.aroundthekorea.exception.model.feign.OpenApiException;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() < 200) {
			return new OpenApiException("open api error", ErrorMessage.BATCH_OPEN_API_ERROR);
		}

		return switch (response.status()) {
			case 404 -> new InvalidRequestException("", ErrorMessage.BATCH_FEIGN_INVALID_REQUEST_HUMAN_ERROR);
			default -> FeignException.errorStatus(methodKey, response);
		};
	}
}
