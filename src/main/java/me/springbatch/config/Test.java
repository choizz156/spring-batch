package me.springbatch.config;


import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
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
				JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
				Date date = jobParameters.getDate("date");
				String name = jobParameters.getString("name");
				Long long1 = jobParameters.getLong("long");

				log.info("date = {}", date);
				log.info("name = {}", name);
				log.info("long1 = {}", long1);

				Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();
				log.info("jobParameters1 = {}", jobParameters1);
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

