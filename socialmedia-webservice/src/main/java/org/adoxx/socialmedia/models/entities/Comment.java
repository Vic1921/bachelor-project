package org.adoxx.socialmedia.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false, length = 1000)
    private String text;

    @ManyToOne
    @JoinColumn(name = "pinId", nullable = false)
    private Pin pin;
}
