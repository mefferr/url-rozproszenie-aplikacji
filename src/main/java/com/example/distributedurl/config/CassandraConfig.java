package com.example.distributedurl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;

    @Override
    protected String getKeyspaceName() {
        return "url";
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        List<CreateKeyspaceSpecification> createKeyspaceSpecifications = new ArrayList<>();
        createKeyspaceSpecifications.add(getKeySpaceSpecification());
        return createKeyspaceSpecifications;
    }

    private CreateKeyspaceSpecification getKeySpaceSpecification() {
        CreateKeyspaceSpecification keyspace = CreateKeyspaceSpecification.createKeyspace("url");
        keyspace.ifNotExists(true).withSimpleReplication();
        return keyspace;
    }

    @Override
    protected List<String> getStartupScripts() {
        return List.of("""
                    CREATE TABLE IF NOT EXISTS url.urlentity(
                      id text primary key,
                      url text,
                      created_timestamp timestamp,
                      last_accessed_timestamp timestamp
                    );
                    """
        );
    }
}
