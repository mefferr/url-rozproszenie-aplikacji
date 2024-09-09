package com.example.distributedurl.repository;

import com.example.distributedurl.entity.UrlEntity;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.Instant;
import java.util.List;

public interface UrlRepository extends CassandraRepository<UrlEntity, String> {

    @AllowFiltering
    List<UrlEntity> findAllByCreatedTimestampBefore(Instant timestamp);

    @AllowFiltering
    List<UrlEntity> findAllByLastAccessedTimestampBefore(Instant timestamp);
}
