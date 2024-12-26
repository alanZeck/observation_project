package com.alan.observation_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ObservationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObservationProjectApplication.class, args);
	}

}
