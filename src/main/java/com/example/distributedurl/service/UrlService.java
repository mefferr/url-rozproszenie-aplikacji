package com.example.distributedurl.service;

import com.example.distributedurl.entity.UrlEntity;
import com.example.distributedurl.model.CreateUrlRequest;
import com.example.distributedurl.model.CreateUrlResponse;
import com.example.distributedurl.model.GetUrlResponse;
import com.example.distributedurl.model.ValidationResponse;
import com.example.distributedurl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final UrlValidationService urlValidationService;

    public CreateUrlResponse save(CreateUrlRequest createUrlRequest) {
        ValidationResponse validationResponse = urlValidationService.isUrlValid(createUrlRequest.getUrl());
        if (!validationResponse.isValid()) {
            throw new IllegalArgumentException("Invalid URL, keywords: " + validationResponse.getForbiddenKeywords());
        }
        Instant now = Instant.now();
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setId(Integer.toHexString(createUrlRequest.getUrl().hashCode()));
        urlEntity.setUrl(createUrlRequest.getUrl());
        urlEntity.setCreatedTimestamp(now);
        urlEntity.setLastAccessedTimestamp(now);
        urlRepository.save(urlEntity);

        var response = CreateUrlResponse.builder()
                .id(urlEntity.getId())
                .url(urlEntity.getUrl())
                .createdTimestamp(urlEntity.getCreatedTimestamp())
                .build();
        log.info("Url created: {}", response.toString());
        return response;
    }

    public GetUrlResponse get(String id) {
        UrlEntity urlEntity = urlRepository.findById(id)
                .orElse(null);
        if (urlEntity == null) {
            log.info("Url for id {} does not exist", id);
            return null;
        }
        urlEntity.setLastAccessedTimestamp(Instant.now());
        urlRepository.save(urlEntity);
        return GetUrlResponse.builder()
                .url(urlEntity.getUrl())
                .createdTimestamp(urlEntity.getCreatedTimestamp())
                .build();
    }
}
