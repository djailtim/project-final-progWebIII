package com.example.apiticket.controller;

import com.example.apiticket.dto.request.TicketRequest;
import com.example.apiticket.dto.request.TicketRequestUpdate;
import com.example.apiticket.entity.Ticket;
import com.example.apiticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public Flux<Ticket> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @PostMapping
    public Mono<Ticket> create(@RequestBody TicketRequest ticketRequest) {
        return ticketService.create(ticketRequest);
    }

    @PutMapping(value = "/{id}")
    public Mono<Ticket> update(@PathVariable String id, @RequestBody TicketRequestUpdate ticketRequestUpdate) {
        return ticketService.update(id, ticketRequestUpdate);
    }
}
