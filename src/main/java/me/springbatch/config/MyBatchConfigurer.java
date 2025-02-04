package me.springbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
public class MyBatchConfigurer extends BasicBatchConfigurer {

	private final DataSource dataSource;

	protected MyBatchConfigurer(
		BatchProperties properties,
		DataSource dataSource,
		TransactionManagerCustomizers transactionManagerCustomizers
	) {
		super(properties, dataSource, transactionManagerCustomizers);
		this.dataSource = dataSource;
	}

	@Override
	protected JobRepository createJobRepository() throws Exception {

		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();

		factory.setDataSource(dataSource);
		factory.setTransactionManager(super.getTransactionManager());
		factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
		factory.setTablePrefix("batch_");

		factory.setMaxVarCharLength(1000);
		return factory.getObject();
	}
}
