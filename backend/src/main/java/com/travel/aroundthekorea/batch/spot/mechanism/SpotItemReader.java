package com.travel.aroundthekorea.batch.spot.mechanism;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

import com.travel.aroundthekorea.batch.spot.api.SpotClient;
import com.travel.aroundthekorea.batch.spot.api.dto.request.SpotClientRequestDto;
import com.travel.aroundthekorea.batch.spot.api.dto.response.PublicDataResponse;
import com.travel.aroundthekorea.common.context.RequestContext;

public class SpotItemReader implements ItemReader<List<PublicDataResponse.Item>> {
	private static final Logger log = LoggerFactory.getLogger(SpotItemReader.class);
	private final RequestContext<SpotClientRequestDto> spotClientRequestContext = new RequestContext<>();
	private final String serviceKey;
	private final SpotClient spotClient;
	private final int rowSize;
	private final String mobileOS;
	private final String mobileApp;
	private final String returnType;
	private String sortBy;
	private int pageNumber;

	public SpotItemReader(SpotClient spotClient, String serviceKey, int page) {
		this.spotClient = spotClient;
		this.serviceKey = serviceKey;
		this.returnType = "JSON";
		this.rowSize = 200;
		this.mobileOS = "ETC";
		this.mobileApp = "atk";
		this.sortBy = "D";
		this.pageNumber = page;
	}

	@Override
	public List<PublicDataResponse.Item> read() {
		spotClientRequestContext.set(
			new SpotClientRequestDto(rowSize,
				pageNumber,
				mobileOS,
				mobileApp,
				returnType,
				serviceKey,
				sortBy)
		);

		PublicDataResponse response = spotClient.getSpots(
			rowSize,
			pageNumber,
			mobileOS,
			mobileApp,
			returnType,
			serviceKey,
			sortBy
		);
		spotClientRequestContext.clear();

		if (isEmpty(response)) {
			return null;
		}

		List<PublicDataResponse.Item> items = response.getResponse().getBody().getItems().getItem();
		finishReading();

		return items;

	}

	private void finishReading() {
		this.pageNumber = Integer.MAX_VALUE;
	}

	private static boolean isEmpty(PublicDataResponse response) {
		return response == null ||
			response.getResponse() == null ||
			response.getResponse().getBody() == null ||
			response.getResponse().getBody().getItems().getItem() == null;
	}

}