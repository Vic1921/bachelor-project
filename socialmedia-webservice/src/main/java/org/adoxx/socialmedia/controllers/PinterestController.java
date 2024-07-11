package org.adoxx.socialmedia.controllers;

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


    @PostMapping("/createBoard")
    public Mono<ResponseEntity<String>> createBoard(@RequestParam String name, @RequestParam String description) {
        return pinterestService.createBoard(name, description)
                .map(ResponseEntity::ok);
    }

    @RequestMapping("/getBoard")
    public Mono<ResponseEntity<String>> getBoard(@RequestParam String boardId) {
        return pinterestService.getBoard(boardId)
                .map(ResponseEntity::ok);
    }

    @RequestMapping("/getBoards")
    public Mono<ResponseEntity<String>> getBoards() {
        return pinterestService.getBoards()
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/deleteBoard")
    public Mono<ResponseEntity<String>> deleteBoard(@RequestParam String boardId) {
        return pinterestService.deleteBoard(boardId)
                .map(ResponseEntity::ok);
    }


    // ---- PIN SERVICE ----


    @RequestMapping("/getPin")
    public Mono<ResponseEntity<String>> getPin(@RequestParam String pinId) {
        return pinService.getPin(pinId)
                .map(ResponseEntity::ok);
    }

    @RequestMapping("/getPins")
    public Mono<ResponseEntity<String>> getPins() {
        return pinService.getPins()
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/deletePin")
    public Mono<ResponseEntity<String>> deletePin(@RequestParam String pinId) {
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


    // ---- HELLO TEST ENDPOINT ----

    @RequestMapping("/hello")
    public String printHello() {
        System.out.println("Hello from PinterestController");
        return "Hello from PinterestController";
    }

}
