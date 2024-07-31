package com.dev.urlShortner.controller;

import com.dev.urlShortner.services.UrlService;
import com.dev.urlShortner.dto.ShortenUrlRequestDto;
import com.dev.urlShortner.dto.ShortenUrlResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequestDto request, HttpServletRequest servletRequest) {
        var response = urlService.shortenUrl(request, servletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") String id) {
        var headers = urlService.redirect(id);

        if(headers == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
