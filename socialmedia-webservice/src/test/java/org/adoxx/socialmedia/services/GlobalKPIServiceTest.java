package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.exceptions.PinException;
import org.adoxx.socialmedia.models.ModelFeedbackOverview;
import org.adoxx.socialmedia.models.entities.Comment;
import org.adoxx.socialmedia.models.entities.Pin;
import org.adoxx.socialmedia.models.responses.CommentDTO;
import org.adoxx.socialmedia.models.responses.SentimentResultDTO;
import org.adoxx.socialmedia.repositories.CommentRepository;
import org.adoxx.socialmedia.repositories.PinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GlobalKPIServiceTest {

    @Mock
    private ISentimentAnalysisService analysisService;

    @Mock
    private PinRepository pinRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ICommentCategoryService categoryService;

    @InjectMocks
    private GlobalKPIService globalKPIService;

    private Pin pin;
    private Comment comment;
    private CommentDTO commentDTO;
    private SentimentResultDTO sentimentResultDTO;

    @BeforeEach
    public void setUp() {
        comment = new Comment();
        comment.setText("Great feature!");
        pin = new Pin();
        pin.setPinId("pin1");
        pin.setComments(Collections.singletonList(comment));
        commentDTO = new CommentDTO("Great feature!", "pin1");
        sentimentResultDTO = new SentimentResultDTO(commentDTO, "positive");
    }

    @Test
    public void testAggregateSentimentData() {
        when(pinRepository.findAll()).thenReturn(Collections.singletonList(pin));
        when(pinRepository.findById(anyString())).thenReturn(java.util.Optional.of(pin));
        when(analysisService.analyzeComments(anyList())).thenReturn(Collections.singletonList(sentimentResultDTO));

        Map<String, Integer> expected = new HashMap<>();
        expected.put("positive", 1);

        Map<String, Integer> result = globalKPIService.aggregateSentimentData();

        assertEquals(expected, result);
        verify(pinRepository, times(1)).findAll();
        verify(pinRepository, times(1)).findById("pin1");
        verify(analysisService, times(1)).analyzeComments(anyList());
    }

    @Test
    public void testAggregateSentimentData_PinNotFound() {
        when(pinRepository.findAll()).thenReturn(Collections.singletonList(pin));
        when(pinRepository.findById(anyString())).thenReturn(java.util.Optional.empty());

        assertThrows(PinException.class, () -> globalKPIService.aggregateSentimentData());
        verify(pinRepository, times(1)).findAll();
        verify(pinRepository, times(1)).findById("pin1");
    }

    @Test
    public void testAggregateComments() {
        Comment comment1 = new Comment();
        comment1.setText("Great feature!");
        comment1.setPin(pin);

        Comment comment2 = new Comment();
        comment2.setText("Needs improvement.");
        comment2.setPin(pin);

        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment1, comment2));
        when(categoryService.categorizeComments(anyList(), eq(true)))
                .thenReturn(new ModelFeedbackOverview("Top Concern", "Favorite Aspect", "Most Requested Feature"));

        ModelFeedbackOverview result = globalKPIService.aggregateComments();

        assertEquals("Top Concern", result.getTopConcerns());
        assertEquals("Favorite Aspect", result.getFavoriteAspects());
        assertEquals("Most Requested Feature", result.getMostRequestedFeatures());
        verify(commentRepository, times(1)).findAll();
        verify(categoryService, times(1)).categorizeComments(anyList(), eq(true));
    }
}