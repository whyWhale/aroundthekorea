package com.travel.aroundthekorea.plan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.aroundthekorea.plan.domain.Calender;
import com.travel.aroundthekorea.plan.domain.Plan;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
	List<Calender> findByPlan(Plan plan);
}
