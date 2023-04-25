package com.example.apiticket.dto.request;

import com.example.apiticket.entity.StatusTicket;

public record TicketRequestUpdate(String title, String description, String requesterId, String status) {
}
