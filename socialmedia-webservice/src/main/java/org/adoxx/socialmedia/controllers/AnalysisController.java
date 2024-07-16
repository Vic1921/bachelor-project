package org.adoxx.socialmedia.controllers;

import org.adoxx.socialmedia.models.Comment;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.adoxx.socialmedia.models.SentimentResult;
import org.adoxx.socialmedia.services.ICommentCategoryService;
import org.adoxx.socialmedia.services.IPinService;
import org.adoxx.socialmedia.services.ISentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
