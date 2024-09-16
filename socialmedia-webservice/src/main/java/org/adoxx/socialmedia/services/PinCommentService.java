package org.adoxx.socialmedia.services;

import lombok.extern.slf4j.Slf4j;
import org.adoxx.socialmedia.exceptions.PinException;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.adoxx.socialmedia.models.entities.Comment;
import org.adoxx.socialmedia.models.entities.Pin;
import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.responses.PinDTO;
import org.adoxx.socialmedia.repositories.CommentRepository;
import org.adoxx.socialmedia.repositories.PinRepository;
import org.adoxx.socialmedia.util.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PinCommentService {

    private final CommentRepository commentRepository;
    private final PinRepository pinRepository;
    private final PinterestWebService pinterestWebService;
    private PinterestServiceImpl pinterestService;

    @Autowired
    public PinCommentService(PinRepository pinRepository, CommentRepository commentRepository, PinterestWebService pinterestWebService, PinterestServiceImpl pinterestService) {
        this.pinRepository = pinRepository;
        this.commentRepository = commentRepository;
        this.pinterestWebService = pinterestWebService;
        this.pinterestService = pinterestService;
    }


    public List<CommentDTO> getPinComments(String pinId) {
        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new PinException("Pin with id: " + pinId + " not found"));

        // Check if comments are already in the database or this is the first time this method is called
        if (pin.getComments().isEmpty()) {
            log.info("No comments found in the database for pin with id: {}. Fetching from Pinterest.", pinId);
            List<String> rawComments = pinterestWebService.fetchComments(pinId);

            log.info("Saving comments for pin with id: {}", pinId);
            List<Comment> newComments = rawComments.stream()
                    .map(commentText -> CommentMapper.toEntity(commentText, pinId))
                    .collect(Collectors.toList());

            pin.setComments(newComments);
            pin.setCommentCount(newComments.size());
            pinRepository.save(pin);
            commentRepository.saveAll(newComments);

            return newComments.stream()
                    .map(comment -> new CommentDTO(comment.getText(), pinId))
                    .collect(Collectors.toList());
        }

        PinDTO pinDTO = pinterestService.getPin(pinId).block();
        assert pinDTO != null;

        int currentCommentCount = pin.getCommentCount();
        log.info("Current comment count for pin with id: {} is: {}", pinId, currentCommentCount);
        int newCommentCount = pinDTO.getPinMetrics().getLifetimeMetrics().getComment();
        log.info("New comment count for pin with id: {} is: {}", pinId, newCommentCount);

        if (currentCommentCount == newCommentCount) {
            log.info("No new comments found for pin with id: {}", pinId);

            return pin.getComments().stream()
                    .map(comment -> new CommentDTO(comment.getText(), pinId))
                    .collect(Collectors.toList());
        }

        log.info("Fetching and updating comments for pin with id: {}", pinId);
        List<String> rawComments = pinterestWebService.fetchComments(pinId);

        log.info("Saving comments for pin with id: {}", pinId);
        List<Comment> newComments = rawComments.stream()
                .map(commentText -> CommentMapper.toEntity(commentText, pinId))
                .collect(Collectors.toList());

        pin.getComments().addAll(newComments);
        pin.setCommentCount(newCommentCount);
        pinRepository.save(pin);
        commentRepository.saveAll(newComments);

        return newComments.stream()
                .map(comment -> new CommentDTO(comment.getText(), pinId))
                .collect(Collectors.toList());
    }

    public void postComment(String pinId, ModelFeedbackOverview categories) {
        log.info("Posting comment for pin with id: {}", pinId);
        String comment = categories.toFormattedComment();

        Pin pin = pinRepository.findById(pinId)
                .orElseThrow(() -> new PinException("Pin with id: " + pinId + " not found"));

        // Check if the comment already exists
        boolean commentExists = pin.getComments().stream()
                .anyMatch(existingComment -> existingComment.getText().equals(comment));

        if (commentExists) {
            log.info("Comment already exists for pin with id: {}", pinId);
            return;
        }

        // Post the comment using the scraper
        log.info("Posting comment for pin with id: {}", pinId);
        pinterestWebService.postComment(pinId, comment);

        // Save the new comment to the database
        Comment newComment = CommentMapper.toEntity(comment, pinId);
        log.info("Saving comment for pin with id: {}", pinId);
        commentRepository.save(newComment);

        pin.getComments().add(newComment);
        pin.setCommentCount(pin.getCommentCount() + 1);
        log.info("Updating comment count for pin with id: {}", pinId);
        pinRepository.save(pin);
    }

}
