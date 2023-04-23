package com.example.apiusers.controller;

import com.example.apiusers.dto.request.UserRequest;
import com.example.apiusers.dto.response.UserResponse;
import com.example.apiusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Flux<UserResponse> getAll() {
        return userService.getAll()
                .map(eachUser -> {
                    return UserResponse.builder()
                            .id(eachUser.getId())
                            .firstName(eachUser.getFirstName())
                            .lastName(eachUser.getLastName())
                            .username(eachUser.getUsername())
                            .createdAt(eachUser.getCreatedAt())
                            .build();
                });
    }

    @PostMapping
    public Mono<UserResponse> create(@RequestBody UserRequest userRequest) {
        return userService.create(userRequest);
    }
}
