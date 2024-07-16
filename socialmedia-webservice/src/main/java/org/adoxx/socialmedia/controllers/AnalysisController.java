package org.adoxx.socialmedia.controllers;

import org.adoxx.socialmedia.models.Comment;
import org.adoxx.socialmedia.models.SentimentResult;
import org.adoxx.socialmedia.services.IPinService;
import org.adoxx.socialmedia.services.ISentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private ISentimentAnalysisService analysisService;

    @Autowired
    private IPinService pinService;

    @GetMapping("/getAnalysis")
    public Mono<ResponseEntity<List<SentimentResult>>> getAnalysis(@RequestParam String pinId) {
        List<Comment> comments = pinService.getPinComments(pinId);

        return (Mono<ResponseEntity<List<SentimentResult>>>) analysisService.analyzeComments(comments);
    }
}
