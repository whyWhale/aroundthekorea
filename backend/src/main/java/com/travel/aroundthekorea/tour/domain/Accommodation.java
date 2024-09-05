package com.travel.aroundthekorea.tour.domain;

import jakarta.persistence.Entity;

@Entity
public class Accommodation extends Tour {

	private String category;
	private String phoneNumber;
	private String priceRange;

	protected Accommodation() {
	}

	public Accommodation(String name, String region, String address, double latitude, double longitude,
		String description,
		String category) {
		super(name, region, address, latitude, longitude, description);
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPriceRange() {
		return priceRange;
	}
}
