package org.adoxx.socialmedia.models.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private String id;
    private String name;
    private String description;
    private int collaboratorCount;
    private int followerCount;
    private String createdAt;

}
