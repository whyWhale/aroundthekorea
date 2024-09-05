package com.travel.aroundthekorea.tour.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // JOINED 전략으로 상속 구조를 관리
@Table(name = "tour")
public abstract class Tour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	private String name;
	private String region;
	private String address;
	private double latitude;
	private double longitude;
	@Lob
	private String description;

	protected Tour() {
	}

	protected Tour(String name, String region, String address, double latitude, double longitude, String description) {
		this.name = name;
		this.region = region;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRegion() {
		return region;
	}

	public String getAddress() {
		return address;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getDescription() {
		return description;
	}
}