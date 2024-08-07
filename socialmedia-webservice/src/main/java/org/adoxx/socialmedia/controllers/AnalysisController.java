package org.adoxx.socialmedia.controllers;

import org.adoxx.socialmedia.models.Comment;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.adoxx.socialmedia.models.SentimentResult;
import org.adoxx.socialmedia.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private DataService dataService;

    @Autowired
    private MockDataService mockDataService;

    @GetMapping("/{pinId}")
    public List<SentimentResult> getAnalysis(@PathVariable String pinId) {
        List<Comment> comments = pinService.getPinComments(pinId);
        return analysisService.analyzeComments(comments);
    }

    @PostMapping("/categorized/{pinId}")
    public ModelFeedbackOverview getCategories(@PathVariable String pinId, @RequestBody List<SentimentResult> sentimentResultList) {
        List<Comment> comments = mockDataService.generateMockComments(20);
        //List<Comment> comments = sentimentResultList.stream().map(SentimentResult::comment).toList();
        return categoryService.categorizeComments(comments);
    }

    @PostMapping("/sentiment-summary/{pinId}")
    public Map<String, Integer> getSentimentSummary(@PathVariable String pinId, @RequestBody List<SentimentResult> sentimentResultList) {
        // Use mock data for testing
        List<SentimentResult> mockData = mockDataService.generateMockSentimentResults(20);
        return dataService.prepareDataForDisplay(mockData);
    }

}
