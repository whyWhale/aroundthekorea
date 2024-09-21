package com.travel.aroundthekorea.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import com.travel.aroundthekorea.batch.spot.mechanism.SpotItemProcessor;
import com.travel.aroundthekorea.batch.spot.mechanism.SpotItemReader;
import com.travel.aroundthekorea.batch.spot.mechanism.SpotItemWriter;
import com.travel.aroundthekorea.batch.spot.api.SpotClient;
import com.travel.aroundthekorea.batch.spot.api.dto.response.PublicDataResponse;
import com.travel.aroundthekorea.batch.spot.listener.JobCompletionNotificationListener;
import com.travel.aroundthekorea.tour.domain.Spot;

@EnableScheduling
@EnableBatchProcessing
@Configuration
public class BatchConfig {
	@Value("${data.service-key}")
	private String serviceKey;

	private static final String SPOT_SELECT_SQL =
		"INSERT INTO spots (title, content_id, address, latitude, longitude, image1, image2, category) " +
			"VALUES (:title, :contentId, :address, :latitude, :longitude, :image1, :image2, :category)";
	private static final int SPOT_CHUNK_SIZE = 200;

	@Bean
	public Job importSpot(JobRepository jobRepository, Step step1,
		JobCompletionNotificationListener listener) {
		return new JobBuilder("batchSpot", jobRepository)
			.listener(listener)
			.start(step1)
			.build();
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	@StepScope
	public SpotItemReader readPublicDataItem(SpotClient client, @Value("#{jobParameters['pageNo']}") int pageNo) {
		return new SpotItemReader(client, serviceKey, pageNo);
	}

	@Bean
	public SpotItemProcessor proccessPublicDataProcessor() {
		return new SpotItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Spot> jdbcBatchItemWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Spot>()
			.dataSource(dataSource)
			.sql(SPOT_SELECT_SQL)
			.beanMapped()
			.build();
	}

	@Bean
	public SpotItemWriter<Spot> publicDataWriter(JdbcBatchItemWriter<Spot> jdbcBatchItemWriter) {
		return new SpotItemWriter<>(jdbcBatchItemWriter);
	}

	@Bean
	public Step getPublicDataStep(
		JobRepository jobRepository,
		PlatformTransactionManager platformTransactionManager,
		SpotItemReader reader,
		SpotItemProcessor processor,
		ItemWriter publicDataWriter
	) {
		return new StepBuilder("SpotProcessingStep", jobRepository)
			.<List<PublicDataResponse.Item>, List<Spot>>chunk(SPOT_CHUNK_SIZE, platformTransactionManager)
			.reader(reader)
			.processor(processor)
			.writer(publicDataWriter)
			.build();
	}
}
