package com.travel.aroundthekorea.tour.domain;

import jakarta.persistence.Entity;

@Entity
public class TourSpot extends Tour {

	private String category;
	private String openingHours;

	protected TourSpot() {
	}

	public TourSpot(String name, String region, String address, double latitude, double longitude, String description,
		String category, String openingHours) {
		super(name, region, address, latitude, longitude, description);
		this.category = category;
		this.openingHours = openingHours;
	}

	public String getCategory() {
		return category;
	}

	public String getOpeningHours() {
		return openingHours;
	}
}
