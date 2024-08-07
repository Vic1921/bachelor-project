package org.adoxx.socialmedia.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.adoxx.socialmedia.models.Comment;
import org.adoxx.socialmedia.models.SentimentResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Service
@Slf4j
public class MockDataService {

    public List<SentimentResult> generateMockSentimentResults(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new SentimentResult(
                        new Comment("Mock comment " + i, "mockPinId"),
                        i % 2 == 0 ? "POSITIVE" : "NEGATIVE"
                ))
                .collect(Collectors.toList());

    }

    public List<Comment> generateMockComments(int count) {
        String[] keywords = {
                "This is a problem", "I love this feature", "There is an issue",
                "This is awesome", "I have a concern", "This is the best",
                "I have a suggestion", "I worry about this", "Please add this feature"
        };

        return IntStream.range(0, count)
                .mapToObj(i -> new Comment(keywords[i % keywords.length] + " " + i, "mockPinId"))
                .collect(Collectors.toList());
    }

}