package com.example.distributedurl.service;

import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.datastax.oss.driver.api.core.cql.Statement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.DeleteSelection;
import com.example.distributedurl.entity.UrlEntity;
import com.example.distributedurl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlCleanupService {
    private final UrlRepository urlRepository;

    public void cleanupCreatedBefore(Instant timestamp) {
        log.info("Removing entites created before {}", timestamp);
        final List<UrlEntity> allByCreatedTimestampBefore = urlRepository.findAllByCreatedTimestampBefore(timestamp);
        urlRepository.deleteAll(allByCreatedTimestampBefore);
        log.info("{} entities deleted", allByCreatedTimestampBefore.size());
    }

    public void cleanupAccessedBefore(Instant timestamp) {
        log.info("Removing entites last accessed before {}", timestamp);
        final List<UrlEntity> allByLastAccessedTimestampBefore = urlRepository.findAllByLastAccessedTimestampBefore(timestamp);
        urlRepository.deleteAll(allByLastAccessedTimestampBefore);
        log.info("{} entities deleted", allByLastAccessedTimestampBefore.size());
    }
}
