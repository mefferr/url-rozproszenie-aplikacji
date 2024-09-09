package com.example.distributedurl.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.Instant;

@Table
@Data
public class UrlEntity {

    @Id
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String id;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String url;

    @SASI(indexMode = SASI.IndexMode.SPARSE)
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column(value = "created_timestamp")
    private Instant createdTimestamp;

    @SASI(indexMode = SASI.IndexMode.SPARSE)
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column(value = "last_accessed_timestamp")
    private Instant lastAccessedTimestamp;
}
