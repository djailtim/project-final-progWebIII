package com.example.apiticket.service;

import com.example.apiticket.dto.TicketMapper;
import com.example.apiticket.dto.request.TicketRequest;
import com.example.apiticket.entity.Ticket;
import com.example.apiticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository repository;
    private final TicketMapper ticketMapper;

    public Flux<Ticket> findAllTickets() {
        return repository.findAll()
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Ticket> create(TicketRequest ticketRequest) {
        return Mono.defer(() -> {
            return repository.save(ticketMapper.mapTicketWithTicketRequestData(ticketRequest))
                    .subscribeOn(Schedulers.boundedElastic());
        });
    }
}
