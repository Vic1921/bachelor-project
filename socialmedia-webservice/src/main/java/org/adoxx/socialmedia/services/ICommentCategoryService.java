package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.Comment;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;

import java.util.List;

public interface ICommentCategoryService {
    ModelFeedbackOverview categorizeComments(List<Comment> text);
}
