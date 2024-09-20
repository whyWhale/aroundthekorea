package com.travel.aroundthekorea.batch.spot.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.aroundthekorea.batch.spot.api.dto.PublicDataResponse;
import com.travel.aroundthekorea.config.FeignConfig;

@FeignClient(
	name = "SpotClient",
	url = "https://apis.data.go.kr/B551011/KorService1",
	configuration = FeignConfig.class
)
public interface SpotClient {
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
}
