package org.adoxx.socialmedia.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.adoxx.socialmedia.models.SentimentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Service
@Slf4j
public class DataService {

    private final ISentimentAnalysisService analysisService;

    @Autowired
    public DataService(ISentimentAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    public Map<String, Integer> prepareDataForDisplay(List<SentimentResult> sentimentResultList) {
        log.info("Preparing data for display... Size of sentimentResultList: " + sentimentResultList.size());

        Map<String, Integer> sentimentSummary = new HashMap<>();
        sentimentSummary.put("POSITIVE", 0);
        sentimentSummary.put("NEGATIVE", 0);
        sentimentSummary.put("NEUTRAL", 0);

        for (SentimentResult result : sentimentResultList) {
            String sentimentKey = result.sentiment().toUpperCase();
            sentimentSummary.put(sentimentKey, sentimentSummary.getOrDefault(sentimentKey, 0) + 1);
        }

        log.info("Data prepared for display: {}", sentimentSummary);
        return sentimentSummary;
    }
}
