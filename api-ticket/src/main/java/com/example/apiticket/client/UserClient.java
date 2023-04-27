package com.example.apiticket.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UserClient {
    private final WebClient webClient;

    private static final String USERS_API_BASE_URL = "http://localhost:8081/users";

    public UserClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<Boolean> findById(String requesterId) {
        return webClient.get()
                .uri(USERS_API_BASE_URL + "/{id}", requesterId)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return Mono.just(true);
                    } else {
                         return Mono.just(false);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
