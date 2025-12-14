package com.example.urlshortner.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")

public class AppConfig {
    private String baseUrl = "http://localhost:8080/";
    private int shortCodeLength = 6;
    private int urlExpiryDays = 30;
}
