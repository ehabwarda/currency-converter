package com.zooplus.challenge.currencyconverter.service;

import com.zooplus.challenge.currencyconverter.entity.User;

public interface UserService {
	
	/**
	 * find user by email.
	 * 
	 * @param email
	 * @return matched user.
	 */
	public User findUserByEmail(String email);
	
	/**
	 * save a user.
	 * 
	 * @param user
	 */
	public void saveUser(User user);
}
