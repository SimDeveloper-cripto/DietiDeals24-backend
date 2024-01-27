package com.exam.ingsw.dietideals24;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class DietiDeals24BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(DietiDeals24BackendApplication.class, args);
	}
}