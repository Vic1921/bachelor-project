package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.Comment;
import org.adoxx.socialmedia.models.SentimentResult;
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
        // Note: The exact sentiment might vary based on the NLP model's interpretation
    }

    @Test
    void testAnalyzeComments() {
        List<Comment> comments = List.of(
                new Comment("I love this!", "123"),
                new Comment("This is terrible.", "456")
        );
        List<SentimentResult> results = sentimentAnalysisService.analyzeComments(comments);
        assertNotNull(results); // Ensure results are returned
        assertEquals(2, results.size()); // Ensure a result is returned for each comment
        // Note: The exact sentiments might vary based on the NLP model's interpretation
    }
}