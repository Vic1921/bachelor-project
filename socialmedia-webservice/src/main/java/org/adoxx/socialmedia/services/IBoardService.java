package org.adoxx.socialmedia.services;

import reactor.core.publisher.Mono;

public interface IBoardService {
    Mono<String> getBoard(String boardId);

    Mono<String> getBoards();

    Mono<String> createBoard(String boardName, String boardDescription);

    Mono<String> deleteBoard(String boardId);

}
