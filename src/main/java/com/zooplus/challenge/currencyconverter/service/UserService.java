package com.zooplus.challenge.currencyconverter.service;

import com.zooplus.challenge.currencyconverter.entity.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
