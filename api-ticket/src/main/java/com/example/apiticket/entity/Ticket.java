package com.example.apiticket.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Builder
@Data
@Document(value = "tickets_db")
public class Ticket {
    private String id;
    private String title;
    private String description;
    private StatusTicket status;
    private String requesterId;
    private Instant createdAt;
    private Instant updatedAt;
}
