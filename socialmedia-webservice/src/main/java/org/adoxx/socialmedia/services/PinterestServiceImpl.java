package org.adoxx.socialmedia.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.adoxx.socialmedia.exceptions.BoardException;
import org.adoxx.socialmedia.exceptions.CommentException;
import org.adoxx.socialmedia.exceptions.PinException;
import org.adoxx.socialmedia.exceptions.PinNotFoundException;
import org.adoxx.socialmedia.models.Comment;
import org.adoxx.socialmedia.models.responses.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Service
@Slf4j
public class PinterestServiceImpl implements IBoardService, IPinService {

    private final WebClient webClient;
    private final PinterestScraperService pinterestScraperService;
    private final ObjectMapper objectMapper;


    // CTOR
    @Autowired
    public PinterestServiceImpl(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.pinterestScraperService = new PinterestScraperService();
        this.objectMapper = objectMapper;
    }


    // ---- CRUD IBoardService methods ----

    @Override
    public Mono<String> createBoard(String name, String description) {
        return webClient.post()
                .uri("/boards")
                .bodyValue(String.format("{\"name\": \"%s\", \"description\": \"%s\"}", name, description))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new BoardException("Failed to create board: " + error.getMessage());
                });
    }

    @Override
    public Mono<String> deleteBoard(String boardId) {
        return webClient.delete()
                .uri("/boards/" + boardId)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new BoardException("Failed to delete board with id: " + boardId + " with error msg: " + error.getMessage());
                });
    }

    @Override
    public Mono<BoardDto> getBoard(String boardId) {
        return webClient.get()
                .uri("/boards/" + boardId)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, BoardDto.class);
                    } catch (Exception e) {
                        log.error("Failed to parse Board response: {}", e.getMessage());
                        throw new BoardException("Failed to parse Board response");
                    }
                });
    }

    @Override
    public Mono<List<BoardDto>> getBoards() {
        return webClient.get().uri("/boards")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, new TypeReference<List<BoardDto>>() {
                        });
                    } catch (Exception e) {
                        log.error("Failed to parse Board response: {}", e.getMessage());
                        throw new BoardException("Failed to parse Board response");
                    }
                });
    }


    // ---- CRUD IPinService methods ----


    @Override
    public Mono<String> getPin(String pinId) {
        return webClient.get()
                .uri("/pins/" + pinId)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new PinNotFoundException("Failed to get pin with id: " + pinId + " with error msg: " + error.getMessage());
                });
    }

    @Override
    public Mono<String> getPins() {
        return webClient.get()
                .uri("/pins")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new PinNotFoundException("Failed to get pins: " + error.getMessage());
                });
    }

    @Override
    public Mono<String> deletePin(String pinId) {
        return webClient.delete()
                .uri("/pins/" + pinId)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new PinNotFoundException("Failed to delete pin with id: " + pinId + " with error msg: " + error.getMessage());
                });
    }

    @Override
    public Mono<String> postPinWithUrl(String boardId, String title, String description, String mediaUrl, String altText) {
        Map<String, Object> mediaSource = Map.of(
                "source_type", "image_url",
                "url", mediaUrl,
                "is_standard", true // Adjust based on beta user status if necessary
        );

        return webClient.post()
                .uri("/pins")
                .bodyValue(Map.of(
                        "board_id", boardId,
                        "title", title,
                        "description", description,
                        "media_source", mediaSource,
                        "alt_text", altText
                ))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new PinException("Failed to post pin with URL: " + error.getMessage());
                });
    }

    @Override
    public Mono<String> postPinWithBase64(String boardId, String title, String description, String base64Image, String altText) {
        Map<String, Object> mediaSource = Map.of(
                "source_type", "image_base64",
                "content_type", "image/png", // or "image/png" based on image format
                "data", base64Image,
                "is_standard", true // Adjust based on beta user status if necessary
        );

        return webClient.post()
                .uri("/pins")
                .bodyValue(Map.of(
                        "board_id", boardId,
                        "title", title,
                        "description", description,
                        "media_source", mediaSource,
                        "alt_text", altText
                ))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new PinException("Failed to post pin with base64 image: " + error.getMessage());
                });
    }


    public List<Comment> getPinComments(String pinId) {
        pinterestScraperService.login(); // Step 1: Login

        List<String> rawComments = pinterestScraperService.fetchComments(pinId); // Step 2: Fetch comments

        if (rawComments.isEmpty()) {
            log.warn("No comments found for pin with id: {}", pinId);
            throw new CommentException("No comments found for pin with id: " + pinId);
        }

        // TODO: Add persistence logic here thru CommentRepository
        // commentRepository.saveAll(comments);

        // Step 3: Map raw comments to Comment records and return the list
        return rawComments.stream()
                .map(comment -> new Comment(comment, pinId))
                .collect(Collectors.toList()); // Return comments or analysis results as needed
    }


}
