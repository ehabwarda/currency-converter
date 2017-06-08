package com.zooplus.challenge.currencyconverter.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.entity.User;
import com.zooplus.challenge.currencyconverter.repository.ExchangeRepository;

@Service
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	private ExchangeRepository exchangeRepository;

	public void saveExchange(Exchange exchange) {
		exchangeRepository.save(exchange);
	}

	public Set<Exchange> getUserExchangHistory(User user) {
		return exchangeRepository.findByUser(user);
	}

}
