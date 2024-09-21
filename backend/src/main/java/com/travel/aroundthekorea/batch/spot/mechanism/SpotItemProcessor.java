package com.travel.aroundthekorea.batch.spot.mechanism;

import static com.travel.aroundthekorea.batch.spot.api.dto.response.PublicDataResponse.*;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.travel.aroundthekorea.batch.spot.api.protocal.Category;
import com.travel.aroundthekorea.tour.domain.Spot;

public class SpotItemProcessor implements ItemProcessor<List<Item>, List<Spot>> {
	private static final String DELIMITER = "-";

	@Override
	public List<Spot> process(List<Item> items) {
		return items.stream().map(item ->
			new Spot(
				item.getTitle(),
				Integer.parseInt(item.getContentid()),
				reformat(item.getAddr1(), item.getAddr2()),
				item.getMapx(),
				item.getMapy(),
				item.getFirstimage(),
				item.getFirstimage2(),
				Category.find(Integer.parseInt(item.getContenttypeid())).name()
			)
		).toList();
	}

	private static String reformat(String frontAddress, String backAddress) {
		return frontAddress + DELIMITER + backAddress;
	}
}
