package com.zooplus.challenge.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zooplus.challenge.currencyconverter.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	 /**
	  * find user by email.
	  * 
	 * @param email
	 * @return matched user.
	 */
	User findByEmail(String email);
}
