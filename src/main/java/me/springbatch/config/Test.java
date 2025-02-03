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
			.start(testStep())
			.build();
	}

	public Step testStep() {
		return stepBuilderFactory.get("testStep")
			.tasklet((contribution, chunkContext) -> {
				log.info("test batch");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

}

