package com.example.apiticket.controller;

import com.example.apiticket.dto.request.TicketRequest;
import com.example.apiticket.dto.request.TicketRequestUpdate;
import com.example.apiticket.entity.Ticket;
import com.example.apiticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Ticket> create(@RequestBody TicketRequest ticketRequest) {
        return ticketService.create(ticketRequest);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Ticket> update(@PathVariable String id, @RequestBody TicketRequestUpdate ticketRequestUpdate) {
        return ticketService.update(id, ticketRequestUpdate);
    }

    @PatchMapping(value = "/{id}/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updateStatus(@PathVariable(value = "id") String id, @PathVariable(value = "status") String status ) {
        return ticketService.updateStatus(id, status);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return ticketService.delete(id);
    }
}
