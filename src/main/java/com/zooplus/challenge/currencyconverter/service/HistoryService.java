package com.zooplus.challenge.currencyconverter.service;

import java.util.Set;

import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.entity.User;

public interface HistoryService {

	/** 
	 * save an exchange to repository.
	 * 
	 * @param exchange
	 */
	public void saveExchange(Exchange exchange);

	/**
	 * get user exchange history.
	 * 
	 * @param user
	 * @return set of user exchanges
	 */
	public Set<Exchange> getUserExchangeHistory(User user);
}
