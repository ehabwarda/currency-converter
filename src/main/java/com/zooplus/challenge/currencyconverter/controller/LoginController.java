package com.zooplus.challenge.currencyconverter.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zooplus.challenge.currencyconverter.entity.User;
import com.zooplus.challenge.currencyconverter.service.UserService;

@Controller
public class LoginController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * list of supported countries.
	 */
	@Value("#{'${config.registeration.countries}'.split(',')}")
	private List<String> countries;	

	/**
	 * get login page.
	 * 
	 * @param model
	 * @return view
	 */
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public String login(Model model){
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}	
	
	/**
	 * get registration page.
	 * 
	 * @param model
	 * @return view
	 */
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public String registration(Model model){
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("countries", countries);
		return "registration";
	}
	
	/**
	 * register new user.
	 * 
	 * @param user
	 * @param bindingResult
	 * @return view
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView registerNewUser(@Valid User user, BindingResult bindingResult) {
		String email = user.getEmail();
		LOGGER.info("Register new user: {}", user.toString());
		ModelAndView modelAndView = new ModelAndView();
		// retrieved logged in user from repository.
		User existingUser = userService.findUserByEmail(email);
		if (existingUser != null) {
			LOGGER.warn("There is already a user registered with this email: {}", email);
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			LOGGER.info("User registered successfully with email: {}", email);
			modelAndView.addObject("successMessage", "User has been registered successfully, please login");
			// clear password
			user.setPassword("");
			modelAndView.addObject("user", user);
			modelAndView.setViewName("login");
			
		}
		return modelAndView;
	}
	
}
