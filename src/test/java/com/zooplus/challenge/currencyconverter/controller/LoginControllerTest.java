package com.zooplus.challenge.currencyconverter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {
	
	@Autowired
    private WebApplicationContext ctx;
	
	 private MockMvc mockMvc;
	    @Before
	    public void setup() {
	        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	        }

	@Test
	public void testLoginGet() throws Exception {
		mockMvc.perform(get("/")).andExpect(view().name("login"));
	}
	
	@Test
	public void testRegistrationGet() throws Exception {
		mockMvc.perform(get("/registration")).andExpect(view().name("registration")).andExpect(model().attributeExists("user"));
	}

}
