package com.example.apiusers.service;

import com.example.apiusers.dto.UserMapper;
import com.example.apiusers.dto.request.UserRequest;
import com.example.apiusers.dto.request.UserRequestUpdate;
import com.example.apiusers.dto.response.UserResponse;
import com.example.apiusers.entity.User;
import com.example.apiusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

    public Flux<UserResponse> getAll() {
        return this.repository.findAll()
                .subscribeOn(Schedulers.boundedElastic())
                .map(userMapper::mapToUserResponse);
    }

    public Mono<UserResponse> create(UserRequest userRequest) {
        return repository.findUserByUsername(userRequest.username())
                .subscribeOn(Schedulers.boundedElastic())
                .thenEmpty(Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists.")))
                .then(Mono.defer(() -> {
                    return repository.save(userMapper.mapUserWithUserRequestData(userRequest))
                            .map(userMapper::mapToUserResponse);
                }));
    }

    public Mono<UserResponse> getInfoUser(String id) {
        return repository.findById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .map(userMapper::mapToUserResponse);
    }

    public Mono<UserResponse> update(String id, UserRequestUpdate userRequestUpdate) {
        return repository.findUserByUsername(userRequestUpdate.username())
                .subscribeOn(Schedulers.boundedElastic())
                .thenEmpty(Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists.")))
                .then(Mono.defer(() -> {
                    return repository.findById(id)
                            .flatMap(user -> repository.save(userMapper.mapUserWithUserRequestUpdateData(user, userRequestUpdate)))
                            .map(userMapper::mapToUserResponse);
                }));
    }

    public Mono<Void> setAdminUser(String id) {
        return repository.findById(id)
                .flatMap(user -> {
                    user.setAdmin(true);
                    return repository.save(user);
                })
                .then();
    }
}
