package com.zooplus.challenge.currencyconverter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/main").setViewName("main");
      registry.addViewController("/").setViewName("main");
      registry.addViewController("/registration").setViewName("registration");
      registry.addViewController("/login").setViewName("login");
  }

}