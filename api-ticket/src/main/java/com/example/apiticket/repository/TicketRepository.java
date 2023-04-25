package com.example.apiticket.repository;

import com.example.apiticket.entity.Ticket;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TicketRepository extends ReactiveCrudRepository<Ticket, String> {
    Flux<Ticket> findAllByRequesterId(String requesterId);
}
