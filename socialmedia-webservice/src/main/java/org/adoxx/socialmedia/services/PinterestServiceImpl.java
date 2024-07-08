package org.adoxx.socialmedia.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.adoxx.socialmedia.exceptions.BoardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Getter
@Service
@Slf4j
public class PinterestServiceImpl implements IPinterestService {

    private final WebClient webClient;

    @Autowired
    public PinterestServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public Mono<String> createBoard(String name, String description) {
        return webClient.post()
                .uri("/boards")
                .bodyValue(String.format("{\"name\": \"%s\", \"description\": \"%s\"}", name, description))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new BoardException("Failed to create board: " + error.getMessage());
                });
    }

    @Override
    public Mono<String> getBoard(String boardId) {
        return webClient.get()
                .uri("/boards/" + boardId)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new BoardException("Failed to get board: " + error.getMessage());
                });
    }

    @Override
    public void getPins() {

    }

    @Override
    public void getPin() {

    }

    @Override
    public void getPinComments() {

    }

    @Override
    public void postPin() {

    }

}
