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

	/* (non-Javadoc)
	 * @see com.zooplus.challenge.currencyconverter.service.HistoryService#saveExchange(com.zooplus.challenge.currencyconverter.entity.Exchange)
	 */
	public void saveExchange(Exchange exchange) {
		exchangeRepository.save(exchange);
	}

	/* (non-Javadoc)
	 * @see com.zooplus.challenge.currencyconverter.service.HistoryService#getUserExchangeHistory(com.zooplus.challenge.currencyconverter.entity.User)
	 */
	public Set<Exchange> getUserExchangeHistory(User user) {
		return exchangeRepository.findByUser(user);
	}

}
