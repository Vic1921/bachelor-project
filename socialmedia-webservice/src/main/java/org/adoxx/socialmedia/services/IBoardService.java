package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.responses.BoardDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBoardService {
    Mono<BoardDto> getBoard(String boardId);

    Mono<List<BoardDto>> getBoards();

    Mono<String> createBoard(String boardName, String boardDescription);

    Mono<String> deleteBoard(String boardId);

}
