package com.example.apiticket.dto;

import com.example.apiticket.dto.request.TicketRequest;
import com.example.apiticket.entity.StatusTicket;
import com.example.apiticket.entity.Ticket;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class TicketMapper {
    public Ticket mapTicketWithTicketRequestData (TicketRequest ticketRequest) {
        return Ticket.builder()
                .id(UUID.randomUUID().toString())
                .title(ticketRequest.title())
                .description(ticketRequest.description())
                .requesterId(ticketRequest.requesterId())
                .status(StatusTicket.NOVO)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
