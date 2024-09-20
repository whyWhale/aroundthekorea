package com.travel.aroundthekorea.batch.spot;

import static com.travel.aroundthekorea.exception.model.ErrorMessage.*;

import java.util.stream.LongStream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.travel.aroundthekorea.exception.model.BatchException;

@Component
public class SpotScheduler {
	private final JobLauncher jobLauncher;
	private final Job importSpot;

	public SpotScheduler(JobLauncher jobLauncher, Job importSpot) {
		this.jobLauncher = jobLauncher;
		this.importSpot = importSpot;
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void runImportSpot() {
		LongStream.rangeClosed(1, 80)
			.forEach(pageNumber -> {
				JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.addLong("pageNo", pageNumber)
					.toJobParameters();

				try {
					jobLauncher.run(importSpot, jobParameters);
				} catch (Exception e) {
					throw new BatchException(e.getMessage(), e.getCause(), BATCH_RUNTIME);
				}
			});
	}

}
