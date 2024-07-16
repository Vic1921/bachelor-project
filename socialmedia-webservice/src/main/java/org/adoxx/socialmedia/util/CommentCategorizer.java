package org.adoxx.socialmedia.util;

import java.util.Set;

public class CommentCategorizer {

    private static final Set<String> CONCERN_KEYWORDS = Set.of("problem", "issue", "concern", "worry");
    private static final Set<String> FAVORITE_KEYWORDS = Set.of("love", "great", "awesome", "best");
    private static final Set<String> FEATURE_KEYWORDS = Set.of("add", "include", "feature", "suggestion");

    public static String categorizeComment(String comment) {
        String lowercaseComment = comment.toLowerCase();
        if (CONCERN_KEYWORDS.stream().anyMatch(lowercaseComment::contains)) {
            return "Top Concern";
        } else if (FAVORITE_KEYWORDS.stream().anyMatch(lowercaseComment::contains)) {
            return "Favorite Aspect";
        } else if (FEATURE_KEYWORDS.stream().anyMatch(lowercaseComment::contains)) {
            return "Most Requested Feature";
        }
        return "Uncategorized";
    }
}