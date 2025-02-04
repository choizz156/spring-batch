package me.springbatch.config;


import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
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
import me.springbatch.tasklet.TaskLet1;
import me.springbatch.tasklet.TaskLet2;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class Test {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final TaskLet1 taskLet1;
	private final TaskLet2 taskLet2;

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
			.tasklet(taskLet1)
			.build();
	}

	@Bean
	public Step testStep2() {
		return stepBuilderFactory.get("testStep2")
			.tasklet(taskLet2)
			.build();
	}
}

