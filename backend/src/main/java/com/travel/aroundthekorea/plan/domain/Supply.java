package com.travel.aroundthekorea.plan.domain;

import com.travel.aroundthekorea.common.context.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "suppliy")
public class Supply extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String word;

	protected Supply() {
	}

	public Supply(String word) {
		this.word = word;
	}

	public Long getId() {
		return id;
	}

	public String getWord() {
		return word;
	}
}