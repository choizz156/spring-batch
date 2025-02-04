package me.springbatch.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobListener implements JobExecutionListener {

	private final JobRepository jobRepository;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("Job execution started");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		String jobName = jobExecution.getJobInstance().getJobName();
		JobParameters jobParameters = new JobParametersBuilder().addString("requestDate", "20250204").toJobParameters();
		JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);

		if(lastJobExecution != null) {
			log.info("Job execution ended");
			for (StepExecution stepExecution : lastJobExecution.getStepExecutions()) {
				log.info("Step execution started");
				log.info("stepExecution.getStatus() = {}", stepExecution.getStatus());
				log.info("stepExecution.getStatus().name() = {}", stepExecution.getStatus().name());
			}
		}
	}
}
