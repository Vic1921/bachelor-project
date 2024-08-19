package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.responses.PinDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IPinService {
    Mono<PinDTO> getPin(String pinId);

    Mono<List<PinDTO>> getPins();

    Mono<String> deletePin(String pinId);

    Mono<String> postPinWithUrl(String boardId, String title, String description, String mediaUrl, String altText);

    Mono<String> postPinWithBase64(String boardId, String title, String description, String mediaUrl, String altText);

}
