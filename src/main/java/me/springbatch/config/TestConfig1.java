package me.springbatch.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.springbatch.listener.JobListener;
import me.springbatch.tasklet.TaskLet1;
import me.springbatch.tasklet.TaskLet2;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestConfig1 {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final TaskLet1 taskLet1;
	private final TaskLet2 taskLet2;
	private final JobListener jobListener;

	@Bean
	public Job testJob() {
		return jobBuilderFactory.get("testJob1")
			.start(testStep1())
			.next(testStep2())
			.listener(jobListener)
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

