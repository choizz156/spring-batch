package me.springbatch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TaskLet1 implements Tasklet {
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		log.info("teak1 = {}", "teak1");

		log.info("stepContribution.getExitStatus().getExitCode() = {}", stepContribution.getExitStatus().getExitCode());
		log.info("stepContribution.getExitStatus() = {}", stepContribution.getExitStatus().addExitDescription("testtest"));
		log.info("stepContribution.getStepExecution().getStepName() = {}",
			stepContribution.getStepExecution().getStepName());
		log.info("stepContribution.getStepExecution().getJobExecution().getJobInstance().getJobName() = {}",
			stepContribution.getStepExecution().getJobExecution().getJobInstance().getJobName());
		return RepeatStatus.FINISHED;
	}
}
