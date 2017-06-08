package com.zooplus.challenge.currencyconverter.controller;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.entity.User;
import com.zooplus.challenge.currencyconverter.service.CurrencyConverterService;
import com.zooplus.challenge.currencyconverter.service.HistoryService;
import com.zooplus.challenge.currencyconverter.service.UserService;

@Controller
public class MainController {

	private final static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private CurrencyConverterService currencyConverterService;

	@Autowired
	private UserService userService;

	@Autowired
	HistoryService historyService;

	/**
	 * get user exchange history.
	 * 
	 * @return view contains user exchange history
	 */
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main() {
		ModelAndView modelAndView = new ModelAndView();
		// retrieve authenticated user - email is used as authentication name
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.findUserByEmail(email);
		LOGGER.info("User record exists in repository: {}", email);

		Exchange exchange = new Exchange();
		// will be empty in first call.
		modelAndView.addObject("exchange", exchange);

		// retrieve user exchange history
		Set<Exchange> userExchanges = historyService.getUserExchangeHistory(user);
		modelAndView.addObject("userExchanges", userExchanges);
		modelAndView.setViewName("main");

		return modelAndView;
	}

	/**
	 * get specific exchange (from currency to another) and the user exchange
	 * history.
	 * 
	 * @param exchange
	 * @return view contains requested exchange rate and user exchange history
	 */
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public ModelAndView exchange(Exchange exchange) {
		ModelAndView modelAndView = new ModelAndView();
		Exchange exchangeResult = null;
		Set<Exchange> userExchanges = null;

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());

			Date date = exchange.getDate();
			if (date == null) {
				// get latest exchange rate
				LOGGER.info("Date not provided, get latest exchange");
				exchangeResult = currencyConverterService.getConversionRate(exchange.getFrom(), exchange.getTo());
			} else {
				// get historical exchange rate
				LOGGER.info("Date provided, get historical exchange in specific date: {}", date);
				exchangeResult = currencyConverterService.getConversionRate(exchange.getFrom(), exchange.getTo(), date);
			}
			exchangeResult.setUser(user);

			LOGGER.info("Exchange retrieved, save exchange to history: {}", exchangeResult.toString());
			historyService.saveExchange(exchangeResult);

			// prepare model for queried exchange
			exchange.setRate(exchangeResult.getRate());
			exchange.setDate(exchangeResult.getDate());
			
			// prepare model for user exchange history
			userExchanges = historyService.getUserExchangeHistory(user);

		} catch (final HttpClientErrorException e) {
			LOGGER.error("Response error: {}", e.getResponseBodyAsString());
			// pass descriptive error message to the view
			modelAndView.addObject("errorMessage", "Currency coverter service returned error response: " + e.getMessage());
		}
		catch (final Exception e) {
			LOGGER.error("Error while retrieving currency exchange", e);
			// pass descriptive error message to the view
			modelAndView.addObject("errorMessage", "Currency coverter error: " + e.getMessage());
		}

		modelAndView.addObject("exchange", exchange);
		modelAndView.addObject("userExchanges", userExchanges);
		modelAndView.setViewName("main");

		return modelAndView;
	}

}
