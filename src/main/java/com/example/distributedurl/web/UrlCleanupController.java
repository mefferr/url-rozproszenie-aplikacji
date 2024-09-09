package com.example.distributedurl.web;

import com.example.distributedurl.service.UrlCleanupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/url-cleanup")
public class UrlCleanupController {
    private final UrlCleanupService urlCleanupService;

    @DeleteMapping("/by-created-timestamp")
    public void byCreatedTimestamp(@RequestParam(name = "timestamp") Instant timestamp) {
        urlCleanupService.cleanupCreatedBefore(timestamp);
    }

    @DeleteMapping("/by-last-accessed-timestamp")
    public void byLastAccessedTimestamp(@RequestParam(name = "timestamp") Instant timestamp) {
        urlCleanupService.cleanupAccessedBefore(timestamp);
    }
}
