package org.adoxx.socialmedia.models.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PinDTO {
    private String id;
    private LocalDateTime createdAt;
    private String link;
    private String title;
    private String description;
    private String boardId;
    private String boardSectionId;
    private String boardOwnerUsername;
    private Map<String, ImageDetails> images;
    private String note;
    private Map<String, Integer> pinMetrics90d;
    private Map<String, Integer> pinMetricsAllTime;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageDetails {
        private int width;
        private int height;
        private String url;

    }
}
