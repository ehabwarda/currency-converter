package com.zooplus.challenge.cucumber;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.zooplus.challenge.CurrencyCoverterApplication;
import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.service.CurrencyConverterService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

@SpringBootTest(classes = CurrencyCoverterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class CurrencyConversionSteps {

	@Autowired
	private CurrencyConverterService currencyConverterService;

	Exchange conversionRate;

	@Given("^call currency service to get currency conversion rate from (.*) to (.*)")
	public void call_currency_service_to_get(String fromCurrency, String toCurrency) {
		conversionRate = currencyConverterService.getConversionRate(fromCurrency, toCurrency);
	}

	@Then("^the conversion rate is (\\d+)")
    public void the_conversion_rate_is(double rate) throws Throwable {
        Assert.assertEquals(1.0, rate, 0.0001);
    }
}
