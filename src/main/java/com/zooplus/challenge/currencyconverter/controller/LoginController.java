package com.zooplus.challenge.currencyconverter.controller;

import java.util.List;

import javax.validation.Valid;

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
	
	@Autowired
	private UserService userService;
	
	/**
	 * list of supported countries.
	 */
	@Value("#{'${config.registeration.countries}'.split(',')}")
	private List<String> countries;	

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public String login(Model model){
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public String registration(Model model){
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("countries", countries);
		return "registration";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User existingUser = userService.findUserByEmail(user.getEmail());
		if (existingUser != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully, please login");
			user.setPassword("");
			modelAndView.addObject("user", user);
			modelAndView.setViewName("login");
			
		}
		return modelAndView;
	}
	
}
