package com.example.apiusers.controller;

import com.example.apiusers.dto.request.UserRequest;
import com.example.apiusers.dto.request.UserRequestUpdate;
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
        return userService.getAll();
    }

    @PostMapping
    public Mono<UserResponse> create(@RequestBody UserRequest userRequest) {
        return userService.create(userRequest);
    }

    @GetMapping("/{id}")
    public Mono<UserResponse> getInfoUser(@PathVariable String id) {
        return userService.getInfoUser(id);
    }

    @PutMapping("/{id}")
    public Mono<UserResponse> updateUser(@PathVariable String id, @RequestBody UserRequestUpdate userRequestUpdate){
        return userService.update(id, userRequestUpdate);
    }

    @PatchMapping("/{id}/set-admin")
    public Mono<Void> setAdminUser(@PathVariable String id) {
        return userService.setAdminUser(id);
    }

}
