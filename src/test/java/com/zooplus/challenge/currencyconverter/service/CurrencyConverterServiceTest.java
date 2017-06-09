package com.zooplus.challenge.currencyconverter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zooplus.challenge.currencyconverter.entity.Exchange;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyConverterServiceTest {

	@Autowired
	CurrencyConverterService currencyConverterService;
	
	@Test
	public void testGetConversionRate() {
		String fromCurrency = "USD";
		String toCurrency = "EUR";
		Exchange exchange = currencyConverterService.getConversionRate(fromCurrency, toCurrency);
		assertNotNull(exchange);
		assertEquals(fromCurrency, exchange.getFrom());
		assertNotNull(exchange.getDate());
		
	}

}
