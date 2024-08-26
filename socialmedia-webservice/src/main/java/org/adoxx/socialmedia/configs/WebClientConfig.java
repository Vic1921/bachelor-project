package org.adoxx.socialmedia.configs;

import lombok.extern.slf4j.Slf4j;
import org.adoxx.socialmedia.exceptions.InitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebClientConfig {


    @Value("${pinterest.access-token}")
    private String accessToken;

    @Bean
    public WebClient webClient() {
        if (accessToken == null || accessToken.isEmpty()) {
            log.error("Pinterest access token is not set. Please set the PINTEREST_ACCESS_TOKEN environment variable.");
            throw new InitializationException("Pinterest access token is not set.");
        }

        log.info("Creating WebClient bean with Pinterest access token.");

        return WebClient.builder()
                .baseUrl("https://api-sandbox.pinterest.com/v5")
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200", "http://localhost:4201")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
