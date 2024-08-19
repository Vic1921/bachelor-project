package org.adoxx.socialmedia.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
    private MediaDetails media;
    private String note;
    @JsonProperty("pin_metrics")
    private PinMetrics pinMetrics;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageDetails {
        private int width;
        private int height;
        private String url;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MediaDetails {
        private Map<String, ImageDetails> images;
        private String media_type;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PinMetrics {
        private MetricDetails lifetimeMetrics;

        @JsonProperty("90d")
        private MetricDetails last90Days;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class MetricDetails {
            private int outboundClick;
            private int impression;

            @JsonFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss Z", timezone = "UTC")
            private LocalDateTime lastUpdated;
            private Integer userFollow;
            private Integer profileVisit;
            private Integer pinClick;
            private Integer save;
            private Integer reaction;
            private Integer comment;
        }
    }
}
