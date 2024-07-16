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
}