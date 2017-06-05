package com.zooplus.challenge.currencyconverter.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.zooplus.challenge.currencyconverter.model.ExchangeRates;

public interface CurrencyConverterService {
	
	public ExchangeRates getConversionRate(String fromCurrency, String toCurrency, Date date);

}
