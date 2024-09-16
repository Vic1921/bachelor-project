package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.adoxx.socialmedia.util.CommentCategorizer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentCategoryServiceImpl implements ICommentCategoryService {

    @Override
    public ModelFeedbackOverview categorizeComments(List<CommentDTO> commentDTOS, boolean isGlobal) {
        StringBuilder topConcerns = new StringBuilder();
        StringBuilder favoriteAspects = new StringBuilder();
        StringBuilder mostRequestedFeatures = new StringBuilder();


        for (CommentDTO commentDTO : commentDTOS) {
            String category = CommentCategorizer.categorizeComment(commentDTO.text());
            switch (category) {
                case "Top Concern":
                    if (isGlobal || topConcerns.isEmpty()) {
                        topConcerns.append(commentDTO.text()).append(" ");
                    }
                    break;
                case "Favorite Aspect":
                    if (isGlobal || favoriteAspects.isEmpty()) {
                        favoriteAspects.append(commentDTO.text()).append(" ");
                    }
                    break;
                case "Most Requested Feature":
                    if (isGlobal || mostRequestedFeatures.isEmpty()) {
                        mostRequestedFeatures.append(commentDTO.text()).append(" ");
                    }
                    break;
            }
        }

        return new ModelFeedbackOverview(topConcerns.toString(), favoriteAspects.toString(), mostRequestedFeatures.toString());
    }
}