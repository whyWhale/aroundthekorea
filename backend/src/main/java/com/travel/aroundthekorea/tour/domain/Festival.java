package com.travel.aroundthekorea.tour.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Festival extends Tour {

	private LocalDate startDate;
	private LocalDate endDate;
	private String location;
	private String organizer;

	protected Festival() {
	}

	public Festival(String name, String region, String address, double latitude, double longitude, String description,
		LocalDate startDate, LocalDate endDate, String location, String organizer) {
		super(name, region, address, latitude, longitude, description);
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.organizer = organizer;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getLocation() {
		return location;
	}

	public String getOrganizer() {
		return organizer;
	}
}
