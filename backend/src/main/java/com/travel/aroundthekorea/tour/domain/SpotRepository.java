package com.travel.aroundthekorea.tour.domain;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
	Page<Spot> findByCreatedAt(PageRequest pageRequest, LocalDateTime localDateTime);
}
