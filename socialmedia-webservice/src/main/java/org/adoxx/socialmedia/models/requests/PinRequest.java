package org.adoxx.socialmedia.models.requests;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PinRequest {
    private String boardId;
    private String title;
    private String description;
    private String mediaUrl;
    private String altText;

}