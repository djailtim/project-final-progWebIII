package com.example.apiusers.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Builder
@Data
@Document(value = "users_db")
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
