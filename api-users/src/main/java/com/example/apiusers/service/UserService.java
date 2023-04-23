package com.example.apiusers.service;

import com.example.apiusers.dto.request.UserRequest;
import com.example.apiusers.dto.response.UserResponse;
import com.example.apiusers.entity.User;
import com.example.apiusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    private static UserResponse userResponseCreate(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public Flux<UserResponse> getAll() {
        return this.repository.findAll()
                .map(UserService::userResponseCreate);
    }

    public Mono<UserResponse> create(UserRequest userRequest) {
        String newId = UUID.randomUUID().toString();
        Mono<User> userCreated = repository.save(User.builder()
                .id(newId)
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .username(userRequest.username())
                .password(userRequest.password())
                .isAdmin(false)
                .isActive(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build());

        return Mono.defer(() -> {
                    return userCreated.map(UserService::userResponseCreate);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<UserResponse> getInfoUser(String id) {
        return repository.findById(id)
                .map(UserService::userResponseCreate);
    }
}
