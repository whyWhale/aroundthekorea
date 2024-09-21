package com.travel.aroundthekorea.batch.spot.api.fallback.feign;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.travel.aroundthekorea.batch.spot.api.SpotClient;
import com.travel.aroundthekorea.batch.spot.api.dto.response.PublicDataResponse;
import com.travel.aroundthekorea.batch.spot.api.protocal.Category;
import com.travel.aroundthekorea.tour.domain.Spot;
import com.travel.aroundthekorea.tour.domain.SpotRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpotClientFallback implements SpotClient {
	private static final Sort CREATED_AT_DESCENDING = Sort.by("createdAt").descending();
	private final SpotRepository spotRepository;
	private final int rowSize = 200;
	private static final String DELIMITER = "-";

	public SpotClientFallback(SpotRepository spotRepository) {
		this.spotRepository = spotRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public PublicDataResponse getSpots(
		int numOfRows,
		int pageNo,
		String mobileOS,
		String mobileApp,
		String type,
		String serviceKey,
		String order) {
		log.error("[spot client error] fallback : 이전데이터들로 대체");
		PageRequest pageRequest = PageRequest.of(pageNo, rowSize, CREATED_AT_DESCENDING);
		Page<Spot> page = spotRepository.findByCreatedAt(pageRequest, LocalDateTime.now().minusDays(1L));
		List<PublicDataResponse.Item> replacements = page.get()
			.map((spot) -> toItemDto(spot))
			.toList();

		return new PublicDataResponse(
			new PublicDataResponse.Response(
				new PublicDataResponse.Header("9999", "재시도 오류로 이전 데이터로 대체합니다."),
				new PublicDataResponse.Body(
					new PublicDataResponse.Items(replacements),
					-1,
					pageNo,
					-1
				)
			)
		);

	}

	private PublicDataResponse.Item toItemDto(Spot spot) {
		return new PublicDataResponse.Item(
			spot.getLatitude(),
			spot.getLongitude(),
			"",
			"",
			"",
			"",
			"",
			spot.getTitle(),
			spot.getAddress(),
			"",
			"",
			"",
			"",
			"",
			"",
			String.valueOf(spot.getContentId()),
			"" + Category.find(spot.getCategory()),
			"",
			"",
			spot.getImage1(),
			spot.getImage2(),
			""
		);
	}

}
