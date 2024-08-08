package org.adoxx.socialmedia.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.adoxx.socialmedia.models.entities.Comment;

import java.util.List;

@Getter
@Setter
@Entity
public class Pin {
    @Id
    private String pinId;
    private int commentCount;

    @OneToMany(mappedBy = "pin", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
