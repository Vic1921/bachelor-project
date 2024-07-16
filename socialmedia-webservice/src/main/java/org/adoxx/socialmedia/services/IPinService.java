package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.Comment;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IPinService {
    Mono<String> getPin(String pinId);

    Mono<String> getPins();

    Mono<String> deletePin(String pinId);

    Mono<String> postPinWithUrl(String boardId, String title, String description, String mediaUrl, String altText);

    Mono<String> postPinWithBase64(String boardId, String title, String description, String mediaUrl, String altText);

    List<Comment> getPinComments(String pinId);
}
