package org.adoxx.socialmedia.models.requests;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PinRequestBase64 {

    private String boardId;
    private String title;
    private String description;
    private String base64Image;
    private String altText;

}