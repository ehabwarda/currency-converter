package com.zooplus.challenge.currencyconverter.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.zooplus.challenge.currencyconverter.model.ExchangeRates;

@Component
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterServiceImpl.class);
	
	/**
	 *  Rest client.
	 */
	@Autowired
	RestTemplate restTemplate;
	
	
	/**
	 * Environment object to get configurations.
	 */
	@Autowired
	Environment env;
	
	/* (non-Javadoc)
	 * @see com.zooplus.challenge.currencyconverter.service.CurrencyConverterService#getConversionRate(java.lang.String, java.lang.String)
	 */
	@Override
	public ExchangeRates getConversionRate(String fromCurrency, String toCurrency) {
		LOGGER.info("Get latest exchange rates, from: {} to: {}", fromCurrency, toCurrency);
		ExchangeRates exchangeRates = null;
		// call real external currency converter service to get latest exchange rates
		String url = String.format(env.getProperty("openexchangerates.endpoint.latest"), env.getProperty("openexchangerates.app-id"), fromCurrency, toCurrency);
		exchangeRates = restTemplate.getForObject(url, ExchangeRates.class);
		LOGGER.info("Response: {}", exchangeRates.toString());
		return exchangeRates;
	}

	/* (non-Javadoc)
	 * @see com.zooplus.challenge.currencyconverter.service.CurrencyConverterService#getConversionRate(java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public ExchangeRates getConversionRate(String fromCurrency, String toCurrency, String date) {
		
		LOGGER.info("Get historical exchange rates, from: {} to: {} in date: {}", fromCurrency, toCurrency, date);
		ExchangeRates exchangeRates = null;
		// call real external currency converter service to get historical exchange rates
		String url = String.format(env.getProperty("openexchangerates.endpoint.historical"), date, env.getProperty("openexchangerates.app-id"), fromCurrency, toCurrency);
		exchangeRates = restTemplate.getForObject(url, ExchangeRates.class);
		LOGGER.info("Response: {}", exchangeRates.toString());
		return exchangeRates;
	}

}
