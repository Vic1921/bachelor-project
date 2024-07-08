package org.adoxx.socialmedia.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.adoxx.socialmedia.exceptions.BoardException;
import org.adoxx.socialmedia.exceptions.InitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

/*    public ResponseEntity<String> init(String accessToken) {
        try {
            this.pinterestClient = new Pinterest(accessToken);
            log.info("Pinterest client initialized successfully.");
            return ResponseEntity.ok("Pinterest client initialized successfully.");
        } catch (Exception e) {
            log.error("Error initializing Pinterest client: ", e);
            throw new InitializationException("Failed to initialize Pinterest client.");
        }
    }*/

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
