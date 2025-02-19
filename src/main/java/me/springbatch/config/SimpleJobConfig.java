package me.springbatch.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.H2PagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SimpleJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource;

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("batchJob")
			.incrementer(new RunIdIncrementer())
			.start(step1())
			.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
			.<Customer, Customer>chunk(100)
			.reader(customItemReader())
			.writer(customItemWriter())
			.build();
	}

	@Bean
	public JdbcBatchItemWriter<Customer> customItemWriter() {
		JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();

		writer.setDataSource(dataSource);
		writer.setSql("insert into customer2 values(:id, :firstName, :lastName, :birthDate)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.afterPropertiesSet();

		return writer;
	}

	@Bean
	public JdbcPagingItemReader<Customer> customItemReader() {

		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();

		reader.setDataSource(dataSource);
		reader.setPageSize(100);
		reader.setRowMapper(new CustomerRowMapper());

		H2PagingQueryProvider h2PagingQueryProvider = new H2PagingQueryProvider();
		h2PagingQueryProvider.setSelectClause("id, firstName, lastName, birthDate");
		h2PagingQueryProvider.setFromClause("from customer");

		Map<String, Order> sortKeys = new HashMap<>(1);

		sortKeys.put("id", Order.ASCENDING);
		h2PagingQueryProvider.setSortKeys(sortKeys);

		reader.setQueryProvider(h2PagingQueryProvider);

		return reader;
	}
}
