package com.zooplus.challenge.currencyconverter.service;

import java.util.Date;
import java.util.Set;

import com.zooplus.challenge.currencyconverter.entity.Exchange;

public interface CurrencyConverterService {
	
	/**
	 * get latest exchange rate.
	 * 
	 * @param fromCurrency
	 * @param toCurrency
	 * @return exchange rate
	 */
	public Exchange getConversionRate(String fromCurrency, String toCurrency);
	
	/**
	 * get exchange rate in specific date (historical).
	 * 
	 * @param fromCurrency
	 * @param toCurrency
	 * @param date
	 * @return exchange rate
	 */
	public Exchange getConversionRate(String fromCurrency, String toCurrency, Date date);
	
	/**
	 * get set of supported currencies.
	 * 
	 * @return set of supported currencies.
	 */
	public Set<String> getSupportedCurrencies();

	/**
	 * get set of configured currencies.
	 * 
	 * @return set of configured currencies.
	 */
	public Set<String> getConfiguredCurrencies();

}
