package me.springbatch.config;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobPramsTest implements ApplicationRunner {

	private final JobLauncher jobLauncher;

	private final Job job;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		JobParameters jobParameters = new JobParametersBuilder().addString("name", "user")
			.addLong("long", 1L)
			.addDate("date", new Date())
			.toJobParameters();

		jobLauncher.run(job, jobParameters);
	}
}
