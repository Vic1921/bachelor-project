package org.adoxx.socialmedia.services;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import jakarta.annotation.PostConstruct;
import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.responses.SentimentResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class SentimentAnalysisServiceImpl implements ISentimentAnalysisService {
    private StanfordCoreNLP pipeline;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    @Override
    public List<SentimentResultDTO> analyzeComments(List<CommentDTO> commentDTOS) {
        return commentDTOS.stream()
                .map(comment -> new SentimentResultDTO(comment, analyzeSentiment(comment.text())))
                .collect(Collectors.toList());
    }

    public String analyzeSentiment(String text) {
        CoreDocument document = pipeline.processToCoreDocument(text);
        for (CoreSentence sentence : document.sentences()) {
            return sentence.sentiment();
        }
        return "NEUTRAL"; // Default if no sentiment is found
    }
}