package com.example.distributedurl.web;

import com.example.distributedurl.model.CreateUrlRequest;
import com.example.distributedurl.model.CreateUrlResponse;
import com.example.distributedurl.model.GetUrlResponse;
import com.example.distributedurl.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/url")
public class UrlController {
    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CreateUrlRequest createUrlRequest) {
        try {
            CreateUrlResponse save = urlService.save(createUrlRequest);
            return ResponseEntity.ok(save);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public RedirectView get(@PathVariable String id) {
        final GetUrlResponse getUrlResponse = urlService.get(id);
        if (getUrlResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found");
        }
        return new RedirectView(getUrlResponse.getUrl());
    }
}
