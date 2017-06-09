package com.zooplus.challenge.currencyconverter.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zooplus.challenge.currencyconverter.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void testSaveUser() {
		User user = new User();
		user.setEmail("test@gmail.com");
		user.setPassword("password");
		user.setName("test");
		user.setLastName("test");
		userService.saveUser(user);

		User retrievedUser = userService.findUserByEmail("test@gmail.com");
		assertEquals(user.getEmail(), retrievedUser.getEmail());
		assertEquals(user.getName(), retrievedUser.getName());
	}

}
