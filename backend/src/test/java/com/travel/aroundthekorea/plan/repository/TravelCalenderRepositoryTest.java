package com.travel.aroundthekorea.plan.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import net.datafaker.Faker;

import com.travel.aroundthekorea.plan.domain.Calender;
import com.travel.aroundthekorea.plan.domain.Plan;
import com.travel.aroundthekorea.plan.domain.TravelCalender;
import com.travel.aroundthekorea.tour.domain.Spot;
import com.travel.aroundthekorea.tour.domain.SpotRepository;

import config.TestJpaConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnitUtil;

@Import(TestJpaConfig.class)
@DataJpaTest(showSql = false)
class TravelCalenderRepositoryTest {
	@Autowired
	TravelCalenderRepository travelCalenderRepository;

	@Autowired
	PlanRepository planRepository;

	@Autowired
	CalenderRepository calenderRepository;

	@Autowired
	SpotRepository spotRepository;

	Faker faker = new Faker();
	List<Calender> jeJuDoCalenders;

	@PersistenceContext
	EntityManager entityManager;

	@BeforeEach
	void setUp() {
		List<Spot> spots = spotRepository.saveAll(Stream.generate(() -> new Spot(
				faker.name().title(),
				faker.number().positive(),
				faker.address().fullAddress(),
				faker.address().latitude(),
				faker.address().longitude(),
				faker.internet().image(),
				faker.internet().image(),
				faker.commerce().brand()
			)).limit(2)
			.toList());
		LinkedList<Spot> spotContainer = new LinkedList<>(spots);
		LocalDate today = LocalDate.now();
		LocalDate endDate = today.plusDays(1);
		Plan jejudoPlan = planRepository.save(new Plan(1L, today, endDate, "jejugo", false));
		jeJuDoCalenders = today.datesUntil(endDate.plusDays(1))
			.map(localDate -> new Calender(jejudoPlan, localDate))
			.toList();
		calenderRepository.saveAll(jeJuDoCalenders);
		jeJuDoCalenders.forEach(
			calender -> travelCalenderRepository.save(new TravelCalender(spotContainer.poll(), calender))
		);

		entityManager.flush();
		entityManager.clear();
	}

	@Test
	@DisplayName("travelCalender 를 조회할떄, spot 또 함께 가져온다.")
	void testFetchJoinWithSpot() {
		//given
		PersistenceUnitUtil persistenceUnitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
		//when
		List<TravelCalender> travelCalenders = travelCalenderRepository.findByCalendarInWithSpots(jeJuDoCalenders);
		//then
		travelCalenders.forEach(travelCalender -> {
			assertThat(persistenceUnitUtil.isLoaded(travelCalender.getSpot())).isTrue();
			assertThat(persistenceUnitUtil.isLoaded(travelCalender.getCalendar())).isTrue();
			assertThat(travelCalender.getSpot()).isNotNull();
		});
	}
}