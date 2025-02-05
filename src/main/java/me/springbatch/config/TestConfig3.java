package me.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
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
public class TestConfig3 {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final TaskLet1 taskLet1;
	private final TaskLet2 taskLet2;
	private final JobListener jobListener;

	@Bean
	public Job testJob3() {
		return jobBuilderFactory.get("flowJob1")
			.start(flow())
			.end()
			.build();
	}

	@Bean
	public Flow flow() {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("testFlow");
		flowBuilder.start(testStep5())
			.next(testStep6())
			.end();

		return flowBuilder.build();
	}

	@Bean
	public Step testStep5() {
		return stepBuilderFactory.get("testStep5")
			.tasklet(taskLet1)
			.build();
	}

	@Bean
	public Step testStep6() {
		return stepBuilderFactory.get("testStep6")
			.tasklet(taskLet2)
			.build();
	}

	// @Bean
	// public Step step3() {
	// 	return stepBuilderFactory.get("step3")
	// 		.tasklet(new Tasklet() {
	// 			@Override
	// 			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
	// 				System.out.println(">> step3 has executed");
	// 				return RepeatStatus.FINISHED;
	// 			}
	// 		})
	// 		.build();
	// }
	// @Bean
	// public Step step4() {
	// 	return stepBuilderFactory.get("step4")
	// 		.tasklet((contribution, chunkContext) -> {
	// 			System.out.println(">> step4 has executed");
	// 			return RepeatStatus.FINISHED;
	// 		})
	// 		.build();
	// }
}

