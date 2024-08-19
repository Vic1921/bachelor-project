package org.adoxx.socialmedia.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pin {
    @Id
    private String pinId;
    private int commentCount;

    @OneToMany(mappedBy = "pin", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
