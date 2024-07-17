package org.adoxx.socialmedia.controllers;

import org.adoxx.socialmedia.models.requests.CreateBoardRequest;
import org.adoxx.socialmedia.models.requests.PinRequest;
import org.adoxx.socialmedia.models.requests.PinRequestBase64;
import org.adoxx.socialmedia.services.IPinService;
import org.adoxx.socialmedia.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
        return pinterestService.createBoard(createBoardRequest.name(), createBoardRequest.description())
                .map(ResponseEntity::ok);
    }

    @GetMapping("/board/{boardId}")
    public Mono<ResponseEntity<String>> getBoard(@PathVariable String boardId) {
        return pinterestService.getBoard(boardId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/boards")
    public Mono<ResponseEntity<String>> getBoards() {
        return pinterestService.getBoards()
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/board/{boardId}")
    public Mono<ResponseEntity<String>> deleteBoard(@PathVariable String boardId) {
        return pinterestService.deleteBoard(boardId)
                .map(ResponseEntity::ok);
    }


    // ---- PIN SERVICE ----


    @GetMapping("/pin/{pinId}")
    public Mono<ResponseEntity<String>> getPin(@PathVariable String pinId) {
        return pinService.getPin(pinId)
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
                .map(ResponseEntity::ok);
    }

    @PostMapping("/pins/url")
    public Mono<ResponseEntity<String>> postPinWithUrl(@RequestBody PinRequest pinRequest) {
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
        return pinService.postPinWithBase64(
                        pinRequestBase64.getBoardId(),
                        pinRequestBase64.getTitle(),
                        pinRequestBase64.getDescription(),
                        pinRequestBase64.getBase64Image(),
                        pinRequestBase64.getAltText())
                .map(ResponseEntity::ok);
    }

}
