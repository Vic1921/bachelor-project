package org.adoxx.socialmedia.services;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IPinterestService {
    Mono<String> getBoard(String boardId);

    Mono<String> createBoard(String boardName, String boardDescription);
    void getPins();
    void getPin();
    void getPinComments();
    void postPin();

    //ResponseEntity<String> init(String accessToken);
}
