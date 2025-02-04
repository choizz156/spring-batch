package me.springbatch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TaskLet1 implements Tasklet {
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		ExecutionContext jobExecutionContext = chunkContext.getStepContext()
			.getStepExecution()
			.getJobExecution()
			.getExecutionContext();

		String jobName = chunkContext.getStepContext()
			.getStepExecution()
			.getJobExecution()
			.getJobInstance()
			.getJobName();

		String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

		ExecutionContext stepExecutionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();

		if (jobExecutionContext.get("jobName") == null) {
			jobExecutionContext.put("jobName", jobName);
		}

		if (stepExecutionContext.get("stepName") == null) {
			stepExecutionContext.put("stepName", stepName);
		}

		String name = jobExecutionContext.get("jobName").toString();
		log.info("jobName = {}", name);
		String name1 = stepExecutionContext.get("stepName").toString();
		log.info("stepName = {}", name1);
		return RepeatStatus.FINISHED;
	}
}
