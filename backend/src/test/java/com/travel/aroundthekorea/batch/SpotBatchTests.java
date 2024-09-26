package com.travel.aroundthekorea.batch;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.travel.aroundthekorea.tour.domain.Spot;
import com.travel.aroundthekorea.tour.domain.SpotRepository;

@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(properties = {
	"data.url=https://apis.data.go.kr/B551011/KorService1"
})
class SpotBatchTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private SpotRepository spotRepository;

	@Autowired
	private Job importSpot;

	@DisplayName("데이터 최신화 하기")
	@Test
	public void updateSpots() {
		// given
		// when
		LongStream.rangeClosed(1, 80).parallel()
			.forEach(pageNumber -> {
				JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.addLong("pageNo", pageNumber)
					.toJobParameters();

				try {
					jobLauncherTestUtils.getJobLauncher().run(importSpot, jobParameters);
				} catch (JobExecutionAlreadyRunningException e) {
					throw new RuntimeException(e);
				} catch (JobRestartException e) {
					throw new RuntimeException(e);
				} catch (JobInstanceAlreadyCompleteException e) {
					throw new RuntimeException(e);
				} catch (JobParametersInvalidException e) {
					throw new RuntimeException(e);
				}
			});
		List<Spot> spots = spotRepository.findAll();

		// then
		assertThat(spots.isEmpty()).isFalse();
		assertThat(spots.size()).isGreaterThan(15000);
	}

}
