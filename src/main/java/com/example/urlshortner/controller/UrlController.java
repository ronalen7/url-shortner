package com.example.urlshortner.controller;

import com.example.urlshortner.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the URL Shortener Service!";
    }

    // return a short url
    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String originalUrl) {
        String shortUrl = urlService.shortenUrl(originalUrl);
        return shortUrl;
    }

    // Redirect to original URL if found
    @GetMapping("/{shortCode}")
    public String getOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        if (originalUrl != null) {
            return originalUrl;
        } else {
            return "URL not found";
        }
    }
}
