package com.zooplus.challenge.currencyconverter.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.zooplus.challenge.currencyconverter.model.ExchangeRates;

@Component
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
	
	@Override
	public ExchangeRates getConversionRate(String fromCurrency, String toCurrency, Date date) {
		ExchangeRates exchangeRates = null;
		// TODO: call real external currency converter service
		return exchangeRates;
	}

}
