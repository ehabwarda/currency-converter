package com.zooplus.challenge.currencyconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zooplus.challenge.currencyconverter.entity.User;
import com.zooplus.challenge.currencyconverter.model.ExchangeRates;
import com.zooplus.challenge.currencyconverter.service.CurrencyConverterService;
import com.zooplus.challenge.currencyconverter.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private CurrencyConverterService currencyConverterService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="main", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("message", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.setViewName("main");
		
		// TODO: pass real parameters
		ExchangeRates conversionRate = currencyConverterService.getConversionRate(null, null, null);
		
		return modelAndView;
	}
	

}
