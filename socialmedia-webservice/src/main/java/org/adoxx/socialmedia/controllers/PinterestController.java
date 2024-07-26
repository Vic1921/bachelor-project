package org.adoxx.socialmedia.controllers;

import org.adoxx.socialmedia.exceptions.BoardException;
import org.adoxx.socialmedia.exceptions.BoardNotFoundException;
import org.adoxx.socialmedia.exceptions.InvalidRequestException;
import org.adoxx.socialmedia.exceptions.PinNotFoundException;
import org.adoxx.socialmedia.models.requests.CreateBoardRequest;
import org.adoxx.socialmedia.models.requests.PinRequest;
import org.adoxx.socialmedia.models.requests.PinRequestBase64;
import org.adoxx.socialmedia.models.responses.BoardDto;
import org.adoxx.socialmedia.services.IPinService;
import org.adoxx.socialmedia.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/pinterest")
public class PinterestController {

    @Autowired
    private IBoardService pinterestService;

    @Autowired
    private IPinService pinService;


    // ---- BOARD SERVICE ----


    @PostMapping("/board")
    public Mono<ResponseEntity<String>> createBoard(@RequestBody CreateBoardRequest createBoardRequest) {
        if (createBoardRequest.name() == null || createBoardRequest.description() == null) {
            throw new InvalidRequestException("Board name and description must be provided");
        }
        return pinterestService.createBoard(createBoardRequest.name(), createBoardRequest.description())
                .map(ResponseEntity::ok);
    }

    @GetMapping("/board/{boardId}")
    public Mono<ResponseEntity<BoardDto>> getBoard(@PathVariable String boardId) {
        return pinterestService.getBoard(boardId)
                .switchIfEmpty(Mono.error(new BoardNotFoundException("Board not found with id: " + boardId)))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/boards")
    public Mono<ResponseEntity<List<BoardDto>>> getBoards() {
        return pinterestService.getBoards()
                .switchIfEmpty(Mono.error(new BoardNotFoundException("No boards found")))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/board/{boardId}")
    public Mono<ResponseEntity<String>> deleteBoard(@PathVariable String boardId) {
        return pinterestService.deleteBoard(boardId)
                .switchIfEmpty(Mono.error(new BoardNotFoundException("Board not found with id: " + boardId)))
                .map(ResponseEntity::ok);
    }


    // ---- PIN SERVICE ----


    @GetMapping("/pin/{pinId}")
    public Mono<ResponseEntity<String>> getPin(@PathVariable String pinId) {
        return pinService.getPin(pinId)
                .switchIfEmpty(Mono.error(new PinNotFoundException("Pin not found with id: " + pinId)))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/getPins")
    public Mono<ResponseEntity<String>> getPins() {
        return pinService.getPins()
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/pin/{pinId}")
    public Mono<ResponseEntity<String>> deletePin(@PathVariable String pinId) {
        return pinService.deletePin(pinId)
                .switchIfEmpty(Mono.error(new PinNotFoundException("Pin not found with id: " + pinId)))
                .map(ResponseEntity::ok);
    }

    @PostMapping("/pins/url")
    public Mono<ResponseEntity<String>> postPinWithUrl(@RequestBody PinRequest pinRequest) {
        if (pinRequest.getBoardId() == null || pinRequest.getTitle() == null ||
                pinRequest.getDescription() == null || pinRequest.getMediaUrl() == null || pinRequest.getAltText() == null) {
            throw new InvalidRequestException("All fields must be provided");
        }

        return pinService.postPinWithUrl(
                pinRequest.getBoardId(),
                pinRequest.getTitle(),
                pinRequest.getDescription(),
                pinRequest.getMediaUrl(),
                pinRequest.getAltText()
        ).map(ResponseEntity::ok);
    }

    @PostMapping("/pins/base64")
    public Mono<ResponseEntity<String>> postPinWithBase64(@RequestBody PinRequestBase64 pinRequestBase64) {
        if (pinRequestBase64.getBoardId() == null || pinRequestBase64.getTitle() == null ||
                pinRequestBase64.getDescription() == null || pinRequestBase64.getBase64Image() == null || pinRequestBase64.getAltText() == null) {
            throw new InvalidRequestException("All fields must be provided");
        }

        return pinService.postPinWithBase64(
                        pinRequestBase64.getBoardId(),
                        pinRequestBase64.getTitle(),
                        pinRequestBase64.getDescription(),
                        pinRequestBase64.getBase64Image(),
                        pinRequestBase64.getAltText())
                .map(ResponseEntity::ok);
    }

}
