package org.adoxx.socialmedia.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adoxx.socialmedia.models.entities.Pin;
import org.adoxx.socialmedia.models.responses.PinDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class PinMapper {
    public static Pin toEntity(PinDTO pinDTO) {
        Pin pin = new Pin();
        pin.setPinId(pinDTO.getId());
        pin.setCommentCount(pinDTO.getPinMetrics().getLifetimeMetrics().getComment());
        return pin;
    }

    public static PinDTO toDTO(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        PinDTO pinDTO = new PinDTO();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Map fields manually
            pinDTO.setId(rootNode.path("id").asText());
            pinDTO.setTitle(rootNode.path("title").asText());
            pinDTO.setDescription(rootNode.path("description").asText());
            pinDTO.setLink(rootNode.path("link").asText(null));
            pinDTO.setBoardId(rootNode.path("board_id").asText());
            pinDTO.setBoardOwnerUsername(rootNode.path("board_owner").path("username").asText());
            pinDTO.setNote(rootNode.path("note").asText());
            pinDTO.setCreatedAt(LocalDateTime.parse(rootNode.path("created_at").asText()));

            // Map media details
            JsonNode mediaNode = rootNode.path("media");
            if (!mediaNode.isMissingNode()) {
                PinDTO.MediaDetails mediaDetails = new PinDTO.MediaDetails();
                mediaDetails.setMedia_type(mediaNode.path("media_type").asText());

                JsonNode imagesNode = mediaNode.path("images");
                if (!imagesNode.isMissingNode()) {
                    Map<String, PinDTO.ImageDetails> imagesMap = new HashMap<>();
                    imagesNode.fields().forEachRemaining(entry -> {
                        JsonNode imageDetailsNode = entry.getValue();
                        PinDTO.ImageDetails imageDetails = new PinDTO.ImageDetails();
                        imageDetails.setWidth(imageDetailsNode.path("width").asInt());
                        imageDetails.setHeight(imageDetailsNode.path("height").asInt());
                        imageDetails.setUrl(imageDetailsNode.path("url").asText());
                        imagesMap.put(entry.getKey(), imageDetails);
                    });
                    mediaDetails.setImages(imagesMap);
                }
                pinDTO.setMedia(mediaDetails);
            }

            // Map pin metrics
            JsonNode pinMetricsNode = rootNode.path("pin_metrics");
            if (!pinMetricsNode.isMissingNode()) {
                PinDTO.PinMetrics pinMetrics = new PinDTO.PinMetrics();

                JsonNode lifetimeMetricsNode = pinMetricsNode.path("lifetime_metrics");
                if (!lifetimeMetricsNode.isMissingNode()) {
                    PinDTO.PinMetrics.MetricDetails lifetimeMetrics = new PinDTO.PinMetrics.MetricDetails();
                    lifetimeMetrics.setComment(lifetimeMetricsNode.path("comment").asInt());
                    lifetimeMetrics.setImpression(lifetimeMetricsNode.path("impression").asInt());

                    pinMetrics.setLifetimeMetrics(lifetimeMetrics);
                }

                JsonNode last90DaysMetricsNode = pinMetricsNode.path("90d");
                if (!last90DaysMetricsNode.isMissingNode()) {
                    PinDTO.PinMetrics.MetricDetails last90DaysMetrics = new PinDTO.PinMetrics.MetricDetails();
                    last90DaysMetrics.setImpression(last90DaysMetricsNode.path("impression").asInt());
                    last90DaysMetrics.setOutboundClick(last90DaysMetricsNode.path("outbound_click").asInt());

                    pinMetrics.setLast90Days(last90DaysMetrics);
                }

                pinDTO.setPinMetrics(pinMetrics);
            }

        } catch (Exception e) {
            // TODO: Handle exceptions, e.g., log an error or throw a custom exception
            e.printStackTrace();
            return null;
        }

        return pinDTO;
    }
}