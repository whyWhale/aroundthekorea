package com.travel.aroundthekorea.plan.domain;

import com.travel.aroundthekorea.tour.domain.Spot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "travel_calender")
@Entity
public class TravelCalender {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "spot_id", referencedColumnName = "id")
	private Spot spot;

	@ManyToOne
	@JoinColumn(name = "calender_id", referencedColumnName = "id")
	private Calender calendar;

	protected TravelCalender() {

	}

	public TravelCalender(Spot spot, Calender calender) {
		this.spot = spot;
		this.calendar = calender;
	}

	public Long getId() {
		return id;
	}

	public Spot getSpot() {
		return spot;
	}

	public Calender getCalendar() {
		return calendar;
	}
}
