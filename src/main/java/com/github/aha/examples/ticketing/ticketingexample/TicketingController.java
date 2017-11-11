package com.github.aha.examples.ticketing.ticketingexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
Napište program, který bude vystavovat REST rozhraní pro ticketovací systém na pobočce. Ten by měl vystavovat následující REST služby:
- Vygenerování pořadového čísla, s tím že vracet bude navíc ještě datum a čas vygenerování a pořadí ve frontě
- Získání aktuálního aktivního čekajícího čísla
- Smazání posledního aktivního čísla

Například:
V seznamu aktivních ticketů bude:
- 1245, 2017-09-01 15:22, 0
- 1246, 2017-09-01 15:42, 1
- 1250, 2017-09-01 16:32, 2

Vygenerování nového vrátí:
- 1251, 2017-09-01 19:20, 3

Získání aktuálního vrátí:
- 1245, 2017-09-01 15:22, 0

Po smazání posledního bude v seznamu:
- 1246, 2017-09-01 15:42, 0
- 1250, 2017-09-01 16:32, 1
- 1251, 2017-09-01 19:20, 2

Úloha by měla být na cca 1-2 hodin.
 */
@RestController
@RequestMapping("/ticketing")
public class TicketingController {
	
	private int lastId;
	
	private Collection<TicketDTO> data;
	
	@PostConstruct
	private void init() {
		lastId = new Random().nextInt(1000);
		
		data = new ArrayList<>();
		createNewTicket();
		createNewTicket();
		createNewTicket();
	}

	private TicketDTO createNewTicket() {
		int nextId = lastId++;
		TicketDTO ticket = new TicketDTO(nextId);
		data.add(ticket);
		return ticket;
	}
	
	// http://localhost:8080/ticketing/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public  Collection<TicketDTO> list() {
		int index = 0;
		for (TicketDTO ticketDTO : data) {
			ticketDTO.setOrder(index++);
		}
		
		return data;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public  TicketDTO add() {
		TicketDTO ticket = createNewTicket();
		ticket.setOrder(data.size() - 1);
		return ticket;
	}

	@RequestMapping(value = "/last", method = RequestMethod.DELETE)
	public void delete() {
		int newSize = data.size() - 1;
		TicketDTO[] newData = Arrays.copyOf(data.toArray(new TicketDTO[newSize]), newSize);
		data = Arrays.asList(newData);
	}

}
