package com.github.aha.examples.ticketing.ticketingexample;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketDTO {
	
	private int id;
	
	private String created;
	
	private int order;

	public TicketDTO(int id) {
		super();
		this.id = id;
		this.created = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date());
	}

	public int getId() {
		return id;
	}

	public String getCreated() {
		return created;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
}
