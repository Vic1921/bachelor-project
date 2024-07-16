package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.Comment;
import org.adoxx.socialmedia.models.SentimentResult;

import java.util.List;

public interface ISentimentAnalysisService {
    List<SentimentResult> analyzeComments(List<Comment> comments);
}
