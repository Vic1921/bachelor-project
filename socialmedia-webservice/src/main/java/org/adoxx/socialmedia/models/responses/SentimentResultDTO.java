package org.adoxx.socialmedia.models.responses;

import org.adoxx.socialmedia.models.responses.CommentDTO;

public record SentimentResultDTO(CommentDTO commentDTO, String sentiment) {
}
