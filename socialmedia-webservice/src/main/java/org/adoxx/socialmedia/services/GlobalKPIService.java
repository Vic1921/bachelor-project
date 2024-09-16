package org.adoxx.socialmedia.services;

import lombok.extern.slf4j.Slf4j;
import org.adoxx.socialmedia.exceptions.PinException;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.adoxx.socialmedia.models.entities.Pin;
import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.responses.SentimentResultDTO;
import org.adoxx.socialmedia.repositories.CommentRepository;
import org.adoxx.socialmedia.repositories.PinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GlobalKPIService {

    private final PinRepository pinRepository;
    private final CommentRepository commentRepository;
    private final ICommentCategoryService categoryService;
    private final ISentimentAnalysisService analysisService;

    @Autowired
    public GlobalKPIService(ISentimentAnalysisService analysisService, PinRepository pinRepository, CommentRepository commentRepository, ICommentCategoryService categoryService) {
        this.analysisService = analysisService;
        this.pinRepository = pinRepository;
        this.commentRepository = commentRepository;
        this.categoryService = categoryService;
    }

    public Map<String, Integer> aggregateSentimentData() {
        Map<String, Integer> sentimentSummary = new HashMap<>();

        List<String> pinIds = pinRepository.findAll().stream()
                .map(Pin::getPinId)
                .toList();

        if (pinIds.isEmpty()) {
            return sentimentSummary;
        }

        log.info("Aggregating sentiment data for {} pins", pinIds.size());


        for (String pinId : pinIds) {
            Pin pin = pinRepository.findById(pinId)
                    .orElseThrow(() -> new PinException("Pin with id: " + pinId + " not found"));

            // Transform to DTOs the comments from the DB
            List<CommentDTO> commentDTOS = pin.getComments().stream()
                    .map(comment -> new CommentDTO(comment.getText(), pinId))
                    .toList();

            if (commentDTOS.isEmpty()) {
                log.info("No comments found for pin with id: {}", pinId);
                continue;
            }

            // Analyze the comments
            List<SentimentResultDTO> sentimentResults = analysisService.analyzeComments(commentDTOS);

            log.info("Sentiment data for pin with id: {} analyzed", pinId);
            // Perform the Sentiment Analysis on all and aggregate the results with merge
            for (SentimentResultDTO result : sentimentResults) {
                sentimentSummary.merge(result.sentiment(), 1, Integer::sum);
            }

            log.info("Sentiment data for pin with id: {} aggregated", pinId);
        }

        return sentimentSummary;
    }

    public ModelFeedbackOverview aggregateComments() {
        List<CommentDTO> globalComments = commentRepository.findAll().stream()
                .map(comment -> new CommentDTO(comment.getText(), comment.getPin().getPinId()))
                .toList();

        return categoryService.categorizeComments(globalComments, true);
    }
}