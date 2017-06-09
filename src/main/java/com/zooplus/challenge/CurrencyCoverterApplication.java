package com.zooplus.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.zooplus.challenge.currencyconverter.entity.User;
import com.zooplus.challenge.currencyconverter.service.UserService;

@SpringBootApplication
@EnableCaching
public class CurrencyCoverterApplication implements CommandLineRunner{
	
	
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(CurrencyCoverterApplication.class, args);
	}

	/* to insert test data while startup.*/
	@Override
	public void run(String... arg0) throws Exception {
		User user = new User();
		user.setEmail("ehab.ward@gmail.com");
		user.setPassword("password");
		user.setName("ehab");
		user.setLastName("warda");
		userService.saveUser(user);
	}
}
