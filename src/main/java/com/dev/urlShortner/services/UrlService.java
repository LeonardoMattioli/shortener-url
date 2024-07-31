package com.dev.urlShortner.services;

import com.dev.urlShortner.dto.ShortenUrlRequestDto;
import com.dev.urlShortner.dto.ShortenUrlResponse;
import com.dev.urlShortner.models.Url;
import com.dev.urlShortner.repositories.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public ShortenUrlResponse shortenUrl(ShortenUrlRequestDto request, HttpServletRequest servletRequest) {
        String id;

        do{
            id = RandomStringUtils.randomAlphanumeric(5, 10);
        } while(urlRepository.existsById(id));

        urlRepository.save(new Url(id, request.url(), LocalDateTime.now().plusMinutes(1)));

        var redirectUrl = servletRequest.getRequestURL().toString().replace("shorten-url", id);

        return new ShortenUrlResponse(redirectUrl);
    }

    public HttpHeaders redirect(String id) {
        var url = urlRepository.findById(id);

        if(url.isEmpty()) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.get().getFullUrl()));

        return headers;
    }
}
