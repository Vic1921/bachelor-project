package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentCategoryServiceImplTest {

    private CommentCategoryServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CommentCategoryServiceImpl();
    }

    @Test
    void testCategorizeComments() {
        // Setup comments for each category
        CommentDTO concernCommentDTO = new CommentDTO("There is a problem with the feature.", "1");
        CommentDTO favoriteCommentDTO = new CommentDTO("I love the new update!", "2");
        CommentDTO featureRequestCommentDTO = new CommentDTO("Can you add more themes?", "3");

        // Call the method under test
        ModelFeedbackOverview overview = service.categorizeComments(List.of(concernCommentDTO, favoriteCommentDTO, featureRequestCommentDTO), false);

        // Assertions to verify each category is correctly populated
        assertTrue(overview.getTopConcerns().contains(concernCommentDTO.text()), "Top Concerns category not correctly populated.");
        assertTrue(overview.getFavoriteAspects().contains(favoriteCommentDTO.text()), "Favorite Aspects category not correctly populated.");
        assertTrue(overview.getMostRequestedFeatures().contains(featureRequestCommentDTO.text()), "Most Requested Features category not correctly populated.");
    }

    @Test
    void testConcatenationOfCommentsInSameCategory() {
        // Setup multiple comments for the same category
        CommentDTO concernComment1DTO = new CommentDTO("There is an issue with the feature.", "1");
        CommentDTO concernComment2DTO = new CommentDTO("Another problem found in the system.", "2");

        // Call the method under test
        ModelFeedbackOverview overview = service.categorizeComments(List.of(concernComment1DTO, concernComment2DTO), true);

        // Assertions to verify comments are concatenated
        String expectedConcatenation = concernComment1DTO.text() + " " + concernComment2DTO.text() + " ";
        assertEquals(expectedConcatenation, overview.getTopConcerns(), "Comments in the same category are concatenated correctly.");
    }
}