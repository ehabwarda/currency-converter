package com.zooplus.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CurrencyCoverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyCoverterApplication.class, args);
	}
}
