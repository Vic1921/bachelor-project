package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;

import java.util.List;

public interface ICommentCategoryService {
    ModelFeedbackOverview categorizeComments(List<CommentDTO> text);
}
