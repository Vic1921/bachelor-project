package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.responses.SentimentResultDTO;

import java.util.List;

public interface ISentimentAnalysisService {
    List<SentimentResultDTO> analyzeComments(List<CommentDTO> commentDTOS);
}
