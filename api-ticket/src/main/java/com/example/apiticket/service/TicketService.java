package com.example.apiticket.service;

import com.example.apiticket.client.UserClient;
import com.example.apiticket.dto.TicketMapper;
import com.example.apiticket.dto.request.TicketRequest;
import com.example.apiticket.dto.request.TicketRequestUpdate;
import com.example.apiticket.entity.StatusTicket;
import com.example.apiticket.entity.Ticket;
import com.example.apiticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository repository;
    private final TicketMapper ticketMapper;
    private final UserClient userClient;

    public Flux<Ticket> findAllTickets() {
        return repository.findAll()
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Ticket> create(TicketRequest ticketRequest) {

        return userClient.findById(ticketRequest.requesterId())
                .flatMap(userExists -> {
                    if(userExists) {
                        return repository.save(ticketMapper.mapTicketWithTicketRequestData(ticketRequest))
                                .subscribeOn(Schedulers.boundedElastic());
                    } else {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Requester user not found."));
                    }
                });
    }

    public Mono<Ticket> update(String id, TicketRequestUpdate ticketRequestUpdate) {
        return repository.findById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(existingTicket -> {
                    existingTicket.setTitle(ticketRequestUpdate.title() == null ? existingTicket.getTitle() : ticketRequestUpdate.title());
                    existingTicket.setDescription(ticketRequestUpdate.description() == null ? existingTicket.getDescription() : ticketRequestUpdate.description());
                    existingTicket.setStatus(StatusTicket.valueOf(ticketRequestUpdate.status()));
                    existingTicket.setUpdatedAt(Instant.now());
                    return repository.save(existingTicket);
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found.")));
    }

    public Mono<Void> updateStatus(String id, String status) {
        return repository.findById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(existingTicket -> {
                    existingTicket.setStatus(StatusTicket.valueOf(status));
                    return repository.save(existingTicket);
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found.")))
                .then();
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found.")))
                .flatMap(repository::delete);
    }
}
