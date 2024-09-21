package com.travel.aroundthekorea.batch.spot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.aroundthekorea.batch.spot.api.dto.request.SpotClientRequestDto;
import com.travel.aroundthekorea.batch.spot.api.dto.response.PublicDataResponse;
import com.travel.aroundthekorea.batch.spot.api.fallback.feign.SpotClientFallback;
import com.travel.aroundthekorea.common.context.RequestContext;
import com.travel.aroundthekorea.common.context.SpringContext;
import com.travel.aroundthekorea.config.FeignConfig;
import com.travel.aroundthekorea.exception.model.BatchException;
import com.travel.aroundthekorea.exception.model.ErrorMessage;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(
	name = "SpotClient",
	url = "https://apis.data.go.kr/B551011/KorService1",
	configuration = FeignConfig.class,
	fallback = SpotClientFallback.class
)
public interface SpotClient {
	Logger log = LoggerFactory.getLogger(SpotClient.class);

	@CircuitBreaker(name = "spotDataApi", fallbackMethod = "finalize")
	@GetMapping(value = "/areaBasedSyncList1", consumes = "application/json")
	PublicDataResponse getSpots(
		@RequestParam("numOfRows") int numOfRows,
		@RequestParam("pageNo") int pageNo,
		@RequestParam("MobileOS") String mobileOS,
		@RequestParam("MobileApp") String mobileApp,
		@RequestParam("_type") String type,
		@RequestParam("serviceKey") String serviceKey,
		@RequestParam("arrange") String order
	);

	default PublicDataResponse finalize(Throwable t) {
		RequestContext<SpotClientRequestDto> requestContext = SpringContext.getBean(RequestContext.class);
		SpotClientFallback spotClientFallback = SpringContext.getBean(SpotClientFallback.class);
		SpotClientRequestDto requestData = requestContext.get();
		log.warn("[CircuitBreaker execute]: numOfRows={}, pageNo={}, serviceKey={}, error: {}",
			requestData.numOfRows(), requestData.pageNo(), requestData.serviceKey(), t.getMessage());

		if (requestData != null) {
			PublicDataResponse replacements = spotClientFallback.getSpots(
				requestData.numOfRows(),
				requestData.pageNo(),
				requestData.mobileOS(),
				requestData.mobileApp(),
				requestData.type(),
				requestData.serviceKey(),
				requestData.order()
			);

			return replacements;
		} else {
			log.error("[CircuitBreaker Fallback] fail: ThreadLocal empty... , error throwable: {}", t.getMessage());
			throw new BatchException("ThreadLocal empty... Fallback Fail",
				t, ErrorMessage.BATCH_BATCH_CIRCUIT_BREAKER_FALLBACK);
		}
	}
}
