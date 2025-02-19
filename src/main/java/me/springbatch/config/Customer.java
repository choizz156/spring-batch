package me.springbatch.config;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Customer {

	private final long id;
	private final String firstName;
	private final String lastName;
	private final Date birthDate;
}
