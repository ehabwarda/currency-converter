package com.zooplus.challenge.cucumber;

import java.util.Set;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.zooplus.challenge.CurrencyCoverterApplication;
import com.zooplus.challenge.currencyconverter.service.CurrencyConverterService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

@SpringBootTest(classes = CurrencyCoverterApplication.class)
@ContextConfiguration
public class GetCurrenciesSteps {

	@Autowired
	private CurrencyConverterService currencyConverterService;

	Set<String> currencies;

	@Given("^call get supported currencies")
	public void call_get_supported_currencies() {
		currencies = currencyConverterService.getSupportedCurrencies();
	}

	@Then("^the result has values")
    public void the_result_has_values() throws Throwable {
        Assert.assertNotNull(currencies);
    }
	
	@Then("^the result contains (.*)")
    public void the_result_contains(String currency) throws Throwable {
        Assert.assertTrue(currencies.contains(currency));
    }
}
