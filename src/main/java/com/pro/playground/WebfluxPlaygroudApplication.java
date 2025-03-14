package com.pro.playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.pro.playground.${sec}")
@EnableR2dbcRepositories(basePackages = "com.pro.playground.${sec}")
public class WebfluxPlaygroudApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxPlaygroudApplication.class, args);
	}

}
