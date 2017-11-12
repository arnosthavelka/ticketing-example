package com.github.aha.examples.ticketing.ticketingexample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
@RequestMapping("/ticket")
public class TicketingController {
	
	private int lastId;
	
	private List<TicketDTO> data;
	
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
	
	// http://localhost:8080/ticket/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public  Collection<TicketDTO> list() {
		int index = 0;
		for (TicketDTO ticketDTO : data) {
			ticketDTO.setOrder(index++);
		}
		
		return data;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public  TicketDTO add() {
		TicketDTO ticket = createNewTicket();
		ticket.setOrder(data.size() - 1);
		return ticket;
	}

	@RequestMapping(value = "/first", method = RequestMethod.GET)
	public TicketDTO getActive() {
		return data.get(0);
	}

	@RequestMapping(value = "/first", method = RequestMethod.DELETE)
	public void delete() {
		data = data.stream().skip(1).collect(Collectors.toList());
	}

}
