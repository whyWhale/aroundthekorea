package com.travel.aroundthekorea.batch.spot.mechanism;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.travel.aroundthekorea.exception.model.ErrorMessage;
import com.travel.aroundthekorea.exception.model.batch.SpotWriteException;

public class SpotItemWriter<T> implements ItemWriter<List<T>> {
	private static final Logger log = LoggerFactory.getLogger(SpotItemWriter.class);
	private final JdbcBatchItemWriter<T> jdbcBatchItemWriter;

	public SpotItemWriter(JdbcBatchItemWriter<T> jdbcBatchItemWriter) {
		this.jdbcBatchItemWriter = jdbcBatchItemWriter;
	}

	@Override
	public void write(Chunk<? extends List<T>> chunk) throws Exception {
		log.info("[BATCH: spot] - write chunk");
		for (List<T> ts : chunk) {
			ts.stream().forEach(v -> {
				try {
					jdbcBatchItemWriter.write(new Chunk<>(v));
				} catch (Exception e) {
					throw new SpotWriteException(e.getMessage(), e.getCause(), ErrorMessage.BATCH_WRITE);
				}
			});
		}
	}
}
