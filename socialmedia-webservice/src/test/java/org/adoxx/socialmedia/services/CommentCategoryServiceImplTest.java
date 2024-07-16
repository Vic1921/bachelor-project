package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.Comment;
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
        Comment concernComment = new Comment("There is a problem with the feature.", "1");
        Comment favoriteComment = new Comment("I love the new update!", "2");
        Comment featureRequestComment = new Comment("Can you add more themes?", "3");

        // Call the method under test
        ModelFeedbackOverview overview = service.categorizeComments(List.of(concernComment, favoriteComment, featureRequestComment));

        // Assertions to verify each category is correctly populated
        assertTrue(overview.getTopConcerns().contains(concernComment.text()), "Top Concerns category not correctly populated.");
        assertTrue(overview.getFavoriteAspects().contains(favoriteComment.text()), "Favorite Aspects category not correctly populated.");
        assertTrue(overview.getMostRequestedFeatures().contains(featureRequestComment.text()), "Most Requested Features category not correctly populated.");
    }

    @Test
    void testConcatenationOfCommentsInSameCategory() {
        // Setup multiple comments for the same category
        Comment concernComment1 = new Comment("There is an issue with the feature.", "1");
        Comment concernComment2 = new Comment("Another problem found in the system.", "2");

        // Call the method under test
        ModelFeedbackOverview overview = service.categorizeComments(List.of(concernComment1, concernComment2));

        // Assertions to verify comments are concatenated
        String expectedConcatenation = concernComment1.text() + concernComment2.text();
        assertNotEquals(expectedConcatenation, overview.getTopConcerns(), "Comments in the same category are not concatenated correctly.");
    }
}