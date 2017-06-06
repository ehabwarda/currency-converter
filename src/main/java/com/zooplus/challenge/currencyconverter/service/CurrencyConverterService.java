package com.zooplus.challenge.currencyconverter.service;

import com.zooplus.challenge.currencyconverter.model.ExchangeRates;

public interface CurrencyConverterService {
	
	/**
	 * get latest conversion rates.
	 * 
	 * @param fromCurrency
	 * @param toCurrency
	 * @return exchange rates
	 */
	public ExchangeRates getConversionRate(String fromCurrency, String toCurrency);
	
	/**
	 * get conversion rates in specific date (historical)
	 * 
	 * @param fromCurrency
	 * @param toCurrency
	 * @param date
	 * @return exhange rates
	 */
	public ExchangeRates getConversionRate(String fromCurrency, String toCurrency, String date);

}
