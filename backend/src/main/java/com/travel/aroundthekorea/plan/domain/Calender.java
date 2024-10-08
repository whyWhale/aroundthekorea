package com.travel.aroundthekorea.plan.domain;

import java.time.LocalDate;
import java.util.StringJoiner;

import com.travel.aroundthekorea.common.context.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "calenders")
public class Calender extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id", referencedColumnName = "id")
	private Plan plan;

	@Column(columnDefinition = "DATE")
	private LocalDate startDate;

	protected Calender() {
	}

	public Calender(Plan plan, LocalDate startDate) {
		this.plan = plan;
		this.startDate = startDate;
	}

	public Long getId() {
		return id;
	}

	public Plan getPlan() {
		return plan;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Calender.class.getSimpleName() + "[", "]")
			.add("id=" + id)
			.add("plan=" + plan)
			.add("startDate=" + startDate)
			.toString();
	}
}
