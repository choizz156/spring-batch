package me.springbatch.config;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements org.springframework.jdbc.core.RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
		return new Customer(resultSet.getLong("id"),
			resultSet.getString("firstName"),
			resultSet.getString("lastName"),
			resultSet.getTimestamp("birthDate")
		);
	}
}
