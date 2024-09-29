package com.travel.aroundthekorea.plan.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "ready_suplies")
@Entity
public class ReadySupplies {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer quantity;

	private Boolean isReady;

	@ManyToOne
	@JoinColumn(name = "plan_id", referencedColumnName = "id")
	private Plan plan;

	@ManyToOne
	@JoinColumn(name = "supply_id", referencedColumnName = "id")
	private Supply supply;

	protected ReadySupplies() {
	}

	public ReadySupplies(Integer quantity, Boolean isReady, Plan plan, Supply supply) {
		this.quantity = quantity;
		this.isReady = isReady;
		this.plan = plan;
		this.supply = supply;
	}

	public Long getId() {
		return id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Boolean getReady() {
		return isReady;
	}

	public Plan getPlan() {
		return plan;
	}

	public Supply getSupply() {
		return supply;
	}
}
