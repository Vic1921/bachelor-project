package org.adoxx.socialmedia.controllers;

import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.adoxx.socialmedia.models.responses.SentimentResultDTO;
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
    private ICommentCategoryService categoryService;

    @Autowired
    private DataService dataService;

    @Autowired
    private PinCommentService pinCommentService;

    @GetMapping("/{pinId}")
    public List<SentimentResultDTO> getAnalysis(@PathVariable String pinId) {
        List<CommentDTO> commentDTOS = pinCommentService.getPinComments(pinId);
        return analysisService.analyzeComments(commentDTOS);
    }

    @PostMapping("/categorized/{pinId}")
    public ModelFeedbackOverview getCategories(@PathVariable String pinId, @RequestBody List<SentimentResultDTO> sentimentResultDTOList) {
        List<CommentDTO> commentDTOS = sentimentResultDTOList.stream().map(SentimentResultDTO::commentDTO).toList();
        return categoryService.categorizeComments(commentDTOS);
    }

    @PostMapping("/sentiment-summary/{pinId}")
    public Map<String, Integer> getSentimentSummary(@PathVariable String pinId, @RequestBody List<SentimentResultDTO> sentimentResultDTOList) {
        return dataService.prepareDataForDisplay(sentimentResultDTOList);
    }

}
