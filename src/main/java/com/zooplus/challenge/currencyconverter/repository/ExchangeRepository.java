package com.zooplus.challenge.currencyconverter.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.entity.User;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {

	/**
	 * get user set of exchanges.
	 * 
	 * @param user
	 * @return user set of exchanges.
	 */
	Set<Exchange> findByUser(User user);

	/**
	 * get latest 10 exchanges for the user.
	 * 
	 * @param user
	 * @return latest 10 exchanges for the user
	 */
	List<Exchange> findFirst10ByUserOrderByQueryDateDesc(User user);

}
