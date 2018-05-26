package com.sumzupp.backendapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories(basePackages = {"com.sumzupp.backendapp"})
@ComponentScan(basePackages = {"com.sumzupp.backendapp"})
@EnableScheduling
@SpringBootApplication
public class SumzuppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SumzuppBackendApplication.class, args);
	}
}
