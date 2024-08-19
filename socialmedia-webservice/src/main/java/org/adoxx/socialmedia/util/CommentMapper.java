package org.adoxx.socialmedia.util;

import org.adoxx.socialmedia.models.entities.Comment;
import org.adoxx.socialmedia.models.entities.Pin;
import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.repositories.PinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommentMapper {

    private static PinRepository pinRepository;

    @Autowired
    public CommentMapper(PinRepository pinRepository) {
        CommentMapper.pinRepository = pinRepository;
    }

    public static Comment toEntity(String commentText, String pinId) {
        Comment comment = new Comment();
        comment.setText(commentText);
        Optional<Pin> pin = pinRepository.findById(pinId);
        pin.ifPresent(comment::setPin);
        return comment;
    }

    public static CommentDTO toDTO(Comment comment) {
        return new CommentDTO(comment.getText(), comment.getPin().getPinId());
    }
}