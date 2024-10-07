package com.travel.aroundthekorea.plan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.aroundthekorea.plan.domain.Plan;
import com.travel.aroundthekorea.plan.domain.ReadySupplies;

public interface ReadySupplyRepository extends JpaRepository<ReadySupplies, Long> {
	List<ReadySupplies> findByPlan(Plan plan);

	@Query(value = "select r from ReadySupplies r join fetch r.supply where r.plan = :plan")
	List<ReadySupplies> findByPlanWithSupply(@Param(value = "plan") Plan plan);
}
