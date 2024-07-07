package org.adoxx.socialmedia.controllers;

import org.adoxx.socialmedia.exceptions.PinterestInitializationException;
import org.adoxx.socialmedia.services.IPinterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pinterest")
public class PinterestController {

    @Autowired
    private IPinterestService pinterestService;

    @GetMapping("/initializePinterest")
    public ResponseEntity<String> initializePinterest(@RequestParam String accessToken) throws PinterestInitializationException {
        pinterestService.init(accessToken);
        return ResponseEntity.ok("Pinterest client initialized.");
    }

    @RequestMapping("/hello")
    public String printHello() {
        System.out.println("Hello from PinterestController");
        return "Hello from PinterestController";
    }

}
