package com.zooplus.challenge.currencyconverter.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.model.ExchangeRates;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
	
	private static final String CONFIG_SUPPORTED_CURRENCIES = "config.supported.currencies";

	private static final String OPENEXCHANGERATES_ENDPOINT_LATEST = "openexchangerates.endpoint.latest";

	private static final String OPENEXCHANGERATES_APP_ID = "openexchangerates.app-id";

	private static final String OPENEXCHANGERATES_ENDPOINT_HISTORICAL = "openexchangerates.endpoint.historical";
	
	private static final String OPENEXCHANGERATES_ENDPOINT_CURRECIES = "openexchangerates.endpoint.currencies";

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

	/**
	 * list of supported currencies.
	 */
	//@Value("#{'${config.supported.currencies}'.split(',')}")
	//private List<String> currencies;
	
	/* (non-Javadoc)
	 * @see com.zooplus.challenge.currencyconverter.service.CurrencyConverterService#getConversionRate(java.lang.String, java.lang.String)
	 */
	@Override
	public Exchange getConversionRate(String fromCurrency, String toCurrency) {
		LOGGER.info("Get latest exchange rates, from: {} to: {}", fromCurrency, toCurrency);
		ExchangeRates exchangeRates = null;
		// call real external currency converter service to get latest exchange rates
		String url = String.format(env.getProperty(OPENEXCHANGERATES_ENDPOINT_LATEST), env.getProperty(OPENEXCHANGERATES_APP_ID), fromCurrency, toCurrency);
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
		String url = String.format(env.getProperty(OPENEXCHANGERATES_ENDPOINT_HISTORICAL), getFormattedDate(date), env.getProperty(OPENEXCHANGERATES_APP_ID), fromCurrency, toCurrency);
		exchangeRates = restTemplate.getForObject(url, ExchangeRates.class);
		LOGGER.info("Response from external service: {}", exchangeRates.toString());
		return getExchange(exchangeRates, fromCurrency, toCurrency, date);
	}
	
	/* (non-Javadoc)
	 * @see com.zooplus.challenge.currencyconverter.service.CurrencyConverterService#getSupportedCurrencies()
	 */
	/*
	 * it will try to get live supported currencies from external service but if
	 * failed, fault tolerance is applied here using hystrix to get default
	 * currencies from configuration.
	 * 
	 * result will be cached also with configured TTL and TTI.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	@HystrixCommand(fallbackMethod = "getConfiguredCurrencies", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500") })
	@Cacheable("currencies")
	public Set<String> getSupportedCurrencies() {
		LOGGER.info("Get set of supported currencies from external service");
		// call real external service to get supported currencies
		Map<String, String> surrenciesMap = restTemplate.getForObject(env.getProperty(OPENEXCHANGERATES_ENDPOINT_CURRECIES), Map.class);
		Set<String> currencies = surrenciesMap.keySet();
		LOGGER.info("Currencies: {}", currencies);	
		return currencies;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getConfiguredCurrencies() {
		LOGGER.info("Get set of supported currencies from configuration");
		Set<String> currencies = env.getProperty(CONFIG_SUPPORTED_CURRENCIES, Set.class);
		LOGGER.info("Configured currencies: {}", currencies);
		return currencies;
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
		// query date to keep order of historical queries
		exchange.setQueryDate(new Date(System.currentTimeMillis()));
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
