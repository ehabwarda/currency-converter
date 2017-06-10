package com.zooplus.challenge.currencyconverter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zooplus.challenge.currencyconverter.entity.Exchange;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyConverterServiceTest {

	private static final String EUR = "EUR";
	private static final String USD = "USD";
	
	@Autowired
	CurrencyConverterService currencyConverterService;
	
	@Test
	public void testGetConversionRate() {
		Exchange exchange = currencyConverterService.getConversionRate(USD, EUR);
		assertNotNull(exchange);
		assertEquals(USD, exchange.getFrom());
		assertNotNull(exchange.getDate());
		
	}
	
	@Test
	public void testGetSupportedCurrencies() {
		assertTrue(currencyConverterService.getSupportedCurrencies().contains(USD));
		
	}

}
