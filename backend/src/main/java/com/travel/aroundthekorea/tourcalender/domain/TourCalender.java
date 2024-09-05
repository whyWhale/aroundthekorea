package com.travel.aroundthekorea.tourcalender.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tour_calender")
public class TourCalender {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	private Integer order;

	protected TourCalender() {

	}

	public TourCalender(Integer order) {
		this.order = order;
	}

	public Integer getOrder() {
		return order;
	}

	public Long getId() {
		return id;
	}
}