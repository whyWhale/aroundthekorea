package com.travel.aroundthekorea.plan.domain;

import java.util.StringJoiner;

import com.travel.aroundthekorea.tour.domain.Spot;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_id", referencedColumnName = "id")
	private Spot spot;

	@ManyToOne(fetch = FetchType.LAZY)
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

	@Override
	public String toString() {
		return new StringJoiner(", ", TravelCalender.class.getSimpleName() + "[", "]")
			.add("id=" + id)
			.add("spot=" + spot)
			.add("calendar=" + calendar)
			.toString();
	}
}
