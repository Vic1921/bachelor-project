package org.adoxx.socialmedia.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelFeedbackOverview {
    private String topConcerns;
    private String favoriteAspects;
    private String mostRequestedFeatures;

    public String toFormattedComment() {
        return String.format("Top Concerns: %s\nFavorite Aspects: %s\nMost Requested Features: %s",
                topConcerns, favoriteAspects, mostRequestedFeatures);
    }
}