package com.example.worrybox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WorryboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorryboxApplication.class, args);
	}

}
