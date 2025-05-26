package com.dku.opensource.priorify.priorify_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class PriorifyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriorifyBackendApplication.class, args);
	}

}
