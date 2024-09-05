package com.travel.aroundthekorea.travellog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "travel_log")
public class TravelLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	private String cartoon;

	private String summary;

	@Lob
	private String contents;

	protected TravelLog() {

	}

	public TravelLog(String cartoon, String summary, String contents) {
		this.cartoon = cartoon;
		this.summary = summary;
		this.contents = contents;
	}
}