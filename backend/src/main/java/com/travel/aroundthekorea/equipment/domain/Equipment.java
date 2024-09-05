package com.travel.aroundthekorea.equipment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipment")
public class Equipment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	private String word;
	private Integer quantity;
	private boolean isReady;

	protected Equipment() {

	}

	public Equipment(String word, Integer quantity, boolean isReady) {
		this.word = word;
		this.quantity = quantity;
		this.isReady = isReady;
	}

	public String getWord() {
		return word;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public boolean isReady() {
		return isReady;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}