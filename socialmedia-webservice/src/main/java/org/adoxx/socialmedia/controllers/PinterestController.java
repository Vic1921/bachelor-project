package org.adoxx.socialmedia.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pinterest")
public class PinterestController {

    // @Autowired
    // private IPinterestService pinterestService;
    // @GetMapping("/boards")
    // public void getBoards() {
    //     pinterestService.getBoards();
    // }

    // @GetMapping("/pins")
    // public void getPins() {
    //     pinterestService.getPins();
    // }

    // @GetMapping("/pin")
    // public void getPin() {
    //     pinterestService.getPin();
    // }

    // @GetMapping("/pin/comments")
    // public void getPinComments() {
    //     pinterestService.getPinComments();
    // }

    // @PostMapping("/pin")
    // public void postPin() {
    //     pinterestService.postPin();
    // }

    @RequestMapping("/hello")
    public String printHello() {
        System.out.println("Hello from PinterestController");
        return "Hello from PinterestController";
    }

}
