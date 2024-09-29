package com.travel.aroundthekorea.plan.domain;

import com.travel.aroundthekorea.common.context.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "travel_diary")
@Entity
public class TravelDiary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "plan_id", referencedColumnName = "id")
	private Plan plan;

	@Lob
	private String content;

	private String cartoon;

	protected TravelDiary() {

	}

	public TravelDiary(Plan plan, String content, String cartoon) {
		this.plan = plan;
		this.content = content;
		this.cartoon = cartoon;
	}

	public Long getId() {
		return id;
	}

	public Plan getPlan() {
		return plan;
	}

	public String getContent() {
		return content;
	}

	public String getCartoon() {
		return cartoon;
	}
}
