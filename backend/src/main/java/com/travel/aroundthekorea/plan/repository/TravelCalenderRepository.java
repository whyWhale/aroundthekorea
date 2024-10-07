package com.travel.aroundthekorea.plan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.aroundthekorea.plan.domain.Calender;
import com.travel.aroundthekorea.plan.domain.TravelCalender;

public interface TravelCalenderRepository extends JpaRepository<TravelCalender, Long> {
	@Query(value = "select tc from TravelCalender tc "
		+ "join fetch tc.spot join fetch tc.calendar "
		+ "where tc.calendar in :calenders")
	List<TravelCalender> findByCalendarInWithSpots(@Param(value = "calenders") List<Calender> calenders);
}
