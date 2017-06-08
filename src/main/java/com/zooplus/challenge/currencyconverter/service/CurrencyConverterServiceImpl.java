package com.zooplus.challenge.currencyconverter.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.model.ExchangeRates;

@Component
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";

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
	public Exchange getConversionRate(String fromCurrency, String toCurrency) {
		LOGGER.info("Get latest exchange rates, from: {} to: {}", fromCurrency, toCurrency);
		ExchangeRates exchangeRates = null;
		// call real external currency converter service to get latest exchange rates
		String url = String.format(env.getProperty("openexchangerates.endpoint.latest"), env.getProperty("openexchangerates.app-id"), fromCurrency, toCurrency);
		exchangeRates = restTemplate.getForObject(url, ExchangeRates.class);		
		LOGGER.info("Response: {}", exchangeRates.toString());	
		return getExchange(exchangeRates, fromCurrency, toCurrency, new Date(1000 * exchangeRates.getTimestamp()));
	}

	/* (non-Javadoc)
	 * @see com.zooplus.challenge.currencyconverter.service.CurrencyConverterService#getConversionRate(java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	@Cacheable("historical-exchanges")
	public Exchange getConversionRate(String fromCurrency, String toCurrency, Date date) {	
		/* will enter here and query external service only if not found in cache.*/
		LOGGER.info("Get historical exchange rates, from: {} to: {} in date: {}", fromCurrency, toCurrency, date);
		ExchangeRates exchangeRates = null;
		// call real external currency converter service to get historical exchange rates
		String url = String.format(env.getProperty("openexchangerates.endpoint.historical"), getFormattedDate(date), env.getProperty("openexchangerates.app-id"), fromCurrency, toCurrency);
		exchangeRates = restTemplate.getForObject(url, ExchangeRates.class);
		LOGGER.info("Response from external service: {}", exchangeRates.toString());
		return getExchange(exchangeRates, fromCurrency, toCurrency, date);
	}
	
	/**
	 * map the response from the external currency converter service to our model.
	 * 
	 * @param exchangeRates
	 * @param fromCurrency
	 * @param toCurrency
	 * @param date
	 * @return mapped exchange object.
	 */
	private Exchange getExchange(ExchangeRates exchangeRates, String fromCurrency, String toCurrency, Date date){
		Exchange exchange = new Exchange();
		exchange.setFrom(fromCurrency);
		exchange.setDate(date);
		exchange.setTo(toCurrency);
		exchange.setRate(exchangeRates.getRates().get(toCurrency));
		return exchange;
		
	}
	
	/**
	 * format date for external currency converter service.
	 * 
	 * @param date to be formated
	 * @return formatted date as required for the currency converter service
	 */
	private String getFormattedDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
		
	}

}
