package com.namutech.spero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SperoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SperoApplication.class, args);
	}

}
