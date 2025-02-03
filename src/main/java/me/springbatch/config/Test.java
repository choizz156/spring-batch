package me.springbatch.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class Test {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job testJob() {
		return jobBuilderFactory.get("testJob")
			.start(testStep1())
			.next(testStep2())
			.build();
	}

	@Bean
	public Step testStep1() {
		return stepBuilderFactory.get("testStep1")
			.tasklet((contribution, chunkContext) -> {
				log.warn("test batch1");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	public Step testStep2() {
		return stepBuilderFactory.get("testStep2")
			.tasklet((contribution, chunkContext) -> {
				log.warn("test batch2");
				return RepeatStatus.FINISHED;
			})
			.build();
	}
}

