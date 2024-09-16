package org.adoxx.socialmedia.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PinRequestNormal {

    private String boardId;
    private String title;
    private String description;
    private MultipartFile image;
    private String altText;
}
