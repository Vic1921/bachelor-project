package org.adoxx.socialmedia.controllers;

import org.adoxx.socialmedia.models.Comment;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.adoxx.socialmedia.models.SentimentResult;
import org.adoxx.socialmedia.services.ICommentCategoryService;
import org.adoxx.socialmedia.services.IPinService;
import org.adoxx.socialmedia.services.ISentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private ISentimentAnalysisService analysisService;

    @Autowired
    private IPinService pinService;

    @Autowired
    private ICommentCategoryService categoryService;

    @GetMapping("/{pinId}")
    public List<SentimentResult> getAnalysis(@PathVariable String pinId) {
        List<Comment> comments = pinService.getPinComments(pinId);

        return analysisService.analyzeComments(comments);
    }

    @GetMapping("/categorized/{pinId}")
    public ModelFeedbackOverview getCategories(@PathVariable String pinId) {
        List<Comment> comments = pinService.getPinComments(pinId);

        return categoryService.categorizeComments(comments);
    }

    // TODO: Move the logic in a specific service
    // and solve NPE from scraperService
    @GetMapping("/sentiment-summary/{pinId}")
    public Map<String, Integer> getSentimentSummary(@PathVariable String pinId) {
        List<Comment> comments = pinService.getPinComments(pinId);
        List<SentimentResult> sentimentResults = analysisService.analyzeComments(comments);

        Map<String, Integer> sentimentSummary = new HashMap<>();
        sentimentSummary.put("positive", 0);
        sentimentSummary.put("negative", 0);
        sentimentSummary.put("neutral", 0);

        for (SentimentResult result : sentimentResults) {
            sentimentSummary.put(result.sentiment(), sentimentSummary.get(result.sentiment()) + 1);
        }

        return sentimentSummary;
    }

}
