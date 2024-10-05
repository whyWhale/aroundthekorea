package com.travel.aroundthekorea.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.aroundthekorea.plan.domain.Plan;

public interface PlanRepository extends JpaRepository<Plan,Long> {
}
