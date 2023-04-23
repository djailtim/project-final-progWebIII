package com.example.apiusers.repository;

import com.example.apiusers.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findFirstByUsername(String username);
}
