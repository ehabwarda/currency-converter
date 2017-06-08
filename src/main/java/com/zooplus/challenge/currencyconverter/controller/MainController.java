package com.zooplus.challenge.currencyconverter.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zooplus.challenge.currencyconverter.entity.Exchange;
import com.zooplus.challenge.currencyconverter.entity.User;
import com.zooplus.challenge.currencyconverter.service.CurrencyConverterService;
import com.zooplus.challenge.currencyconverter.service.HistoryService;
import com.zooplus.challenge.currencyconverter.service.UserService;

@Controller
public class MainController {
	
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
	@RequestMapping(value="main", method = RequestMethod.GET)
	public ModelAndView main(){
		ModelAndView modelAndView = new ModelAndView();
		// retrieve authenticated user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());		
		
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
	 * get specific exchange (from currency to another) and the user exchange history.
	 * 
	 * @param exchange
	 * @return view contains requested exchange rate and user exchange history
	 */
	@RequestMapping(value="main", method = RequestMethod.POST)
	public ModelAndView exchange(Exchange exchange){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
				
		Exchange exchangeResult = null;
		if(exchange.getDate() == null){
			// get latest exchange rate
			exchangeResult= currencyConverterService.getConversionRate(exchange.getFrom(), exchange.getTo());
		}
		else{
			// get historical exchange rate
			exchangeResult= currencyConverterService.getConversionRate(exchange.getFrom(), exchange.getTo(), exchange.getDate());
		}
		exchangeResult.setUser(user);
		
		historyService.saveExchange(exchangeResult);
		
		// prepare model for queried exchange
		exchange.setRate(exchangeResult.getRate());
		exchange.setDate(exchangeResult.getDate());
		modelAndView.addObject("exchange", exchange);
		
		// prepare model for user exchange history
		Set<Exchange> userExchanges = historyService.getUserExchangeHistory(user);
		modelAndView.addObject("userExchanges", userExchanges);
		modelAndView.setViewName("main");
		
		return modelAndView;
	}
	

}
