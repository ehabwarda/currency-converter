package com.zooplus.challenge.currencyconverter.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.entity.User;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Integer>{
	Set<Exchange> findByUser(User user);

}
