package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.responses.SentimentResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SentimentAnalysisServiceImplTest {

    private SentimentAnalysisServiceImpl sentimentAnalysisService;

    @BeforeEach
    void setUp() {
        sentimentAnalysisService = new SentimentAnalysisServiceImpl();
        sentimentAnalysisService.init(); // Initialize the service
    }

    @Test
    void testAnalyzeSentiment() {
        String text = "I love this product!";
        String sentiment = sentimentAnalysisService.analyzeSentiment(text);
        assertNotNull(sentiment); // Ensure a sentiment is returned
    }

    @Test
    void testAnalyzeComments() {
        List<CommentDTO> commentDTOS = List.of(
                new CommentDTO("I love this!", "123"),
                new CommentDTO("This is terrible.", "456")
        );
        List<SentimentResultDTO> results = sentimentAnalysisService.analyzeComments(commentDTOS);
        assertNotNull(results); // Ensure results are returned
        assertEquals(2, results.size()); // Ensure a result is returned for each comment
    }
}