package me.springbatch;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import me.springbatch.config.Customer;
import me.springbatch.config.CustomerRowMapper;
import me.springbatch.config.SimpleJobConfig;

@SpringBatchTest
@SpringBootTest(classes = {SimpleJobConfig.class, TestConfig.class})
class BatchTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void test1() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addString("name", "user1")
			.addLong("date", new Date().getTime())
			.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		assertThat(jobExecution.getStatus()).isEqualByComparingTo(BatchStatus.COMPLETED);
		assertThat(jobExecution.getExitStatus()).isEqualByComparingTo(ExitStatus.COMPLETED);
	}
}
