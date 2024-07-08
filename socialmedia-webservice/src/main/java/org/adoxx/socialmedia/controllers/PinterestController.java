package org.adoxx.socialmedia.controllers;

import org.adoxx.socialmedia.exceptions.InitializationException;
import org.adoxx.socialmedia.services.IPinterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pinterest")
public class PinterestController {

    @Autowired
    private IPinterestService pinterestService;

    @RequestMapping("/createBoard")
    public Mono<ResponseEntity<String>> createBoard(@RequestParam String name, @RequestParam String description) {
        return pinterestService.createBoard(name, description)
                .map(ResponseEntity::ok);
    }

    @RequestMapping("/getBoard")
    public Mono<ResponseEntity<String>> getBoard(@RequestParam String boardId) {
        return pinterestService.getBoard(boardId)
                .map(ResponseEntity::ok);
    }

    @RequestMapping("/hello")
    public String printHello() {
        System.out.println("Hello from PinterestController");
        return "Hello from PinterestController";
    }

}
