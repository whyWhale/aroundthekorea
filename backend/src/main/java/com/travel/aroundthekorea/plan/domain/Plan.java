package com.travel.aroundthekorea.plan.domain;

import java.time.LocalDate;

import com.travel.aroundthekorea.common.context.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "plan")
@Entity
public class Plan extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;

	private String title;

	@Column(columnDefinition = "DATE")
	private LocalDate startDate;

	@Column(columnDefinition = "DATE")
	private LocalDate endDate;

	@Column(name = "is_completed")
	private Boolean isCompleted;

	protected Plan() {

	}

	public Plan(Long userId, LocalDate startDate, LocalDate endDate, String title, Boolean isCompleted) {
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.isCompleted = isCompleted;
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getTitle() {
		return title;
	}

	public Boolean getCompleted() {
		return isCompleted;
	}

	public boolean isHost(Long readerId) {
		return this.userId.equals(readerId);
	}
}
