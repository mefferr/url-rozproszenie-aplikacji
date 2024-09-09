package com.example.distributedurl.service;

import com.example.distributedurl.config.ForbiddenKeywordConfig;
import com.example.distributedurl.model.UrlError;
import com.example.distributedurl.model.ValidationResponse;
import com.example.distributedurl.stream.UrlErrorProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlValidationService {
    private final UrlErrorProducer urlErrorProducer;

    public ValidationResponse isUrlValid(String url) {
        var forbiddenKeywords = ForbiddenKeywordConfig.FORBIDDEN_KEYWORDS.stream()
                .filter(url::contains)
                .toList();
        if (!forbiddenKeywords.isEmpty()) {
            urlErrorProducer.send(UrlError.builder()
                            .url(url)
                            .forbiddenKeywords(forbiddenKeywords)
                    .build());
        }
        return ValidationResponse.builder()
                .valid(forbiddenKeywords.isEmpty())
                .forbiddenKeywords(forbiddenKeywords)
                .build();
    }
}
