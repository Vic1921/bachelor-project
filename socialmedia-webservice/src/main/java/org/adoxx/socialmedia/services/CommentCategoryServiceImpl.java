package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.adoxx.socialmedia.util.CommentCategorizer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentCategoryServiceImpl implements ICommentCategoryService {

    @Override
    public ModelFeedbackOverview categorizeComments(List<CommentDTO> commentDTOS) {
        StringBuilder topConcerns = new StringBuilder();
        StringBuilder favoriteAspects = new StringBuilder();
        StringBuilder mostRequestedFeatures = new StringBuilder();

        for (CommentDTO commentDTO : commentDTOS) {
            String category = CommentCategorizer.categorizeComment(commentDTO.text());
            switch (category) {
                case "Top Concern":
                    if (topConcerns.isEmpty()) {
                        topConcerns.append(commentDTO.text());
                    }
                    break;
                case "Favorite Aspect":
                    if (favoriteAspects.isEmpty()) {
                        favoriteAspects.append(commentDTO.text());
                    }
                    break;
                case "Most Requested Feature":
                    if (mostRequestedFeatures.isEmpty()) {
                        mostRequestedFeatures.append(commentDTO.text());
                    }
                    break;
                default:
                    // Handle Uncategorized or add an Uncategorized list if needed
                    break;
            }
        }

        return new ModelFeedbackOverview(topConcerns.toString(), favoriteAspects.toString(), mostRequestedFeatures.toString());
    }
}