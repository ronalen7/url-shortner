package com.example.urlshortner.service;

import com.example.urlshortner.model.UrlMapping;
import com.example.urlshortner.repository.UrlRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final String BASE_URL = "http://short.ly/";
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int SHORT_URL_LENGTH = 6;
    private final Random random = new Random();

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // Returns the full short URL (with BASE_URL) for the client
    public String shortenUrl(String originalUrl) {
        // basic validation
        if (originalUrl == null || originalUrl.isBlank()) {
            throw new IllegalArgumentException("originalUrl must not be empty");
        }

        String shortCode;
        // generate until we get a unique code
        do {
            shortCode = generateShortCode();
        } while (urlRepository.findByShortCode(shortCode).isPresent());

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortCode(shortCode);
        urlRepository.save(urlMapping);
        return BASE_URL + shortCode;
//        return shortCode;
    }

    // Returns original URL or null if not found
    public String getOriginalUrl(String shortCode) {
        if (shortCode == null) return null;
        Optional<UrlMapping> urlOpt = urlRepository.findByShortCode(shortCode);
        return urlOpt.map(UrlMapping::getOriginalUrl).orElse(null);
    }

    private String generateShortCode() {
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(ALPHABET.length());
            shortCode.append(ALPHABET.charAt(index));
        }
        return shortCode.toString();
    }
}