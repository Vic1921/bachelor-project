package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.Comment;

import java.util.List;

public interface ISentimentAnalysisService {

    String analyzeSentiment(String text);
}
