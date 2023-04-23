package com.example.apiusers.dto.response;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
@Builder
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private Instant createdAt;
}
