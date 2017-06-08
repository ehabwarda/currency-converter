package com.zooplus.challenge.currencyconverter.service;

import java.util.Set;

import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.entity.User;

public interface HistoryService {

	public void saveExchange(Exchange exchange);

	public Set<Exchange> getUserExchangHistory(User user);
}
