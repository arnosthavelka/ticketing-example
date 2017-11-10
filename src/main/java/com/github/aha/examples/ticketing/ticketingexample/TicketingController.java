package com.github.aha.examples.ticketing.ticketingexample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticketing")
public class TicketingController {
	
	// http://localhost:8080/ticketing/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String hello() {
		return "Hello!";
	}

}
