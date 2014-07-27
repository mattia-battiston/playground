package com.example.helloworld.health;

import com.codahale.metrics.health.HealthCheck;
import com.example.helloworld.repositories.DatabaseConnectionPool;

import java.sql.Connection;

public class DatabaseHealthCheck extends HealthCheck {

    public static final int CONNECTION_TIMEOUT_IN_SECONDS = 1;
    private final DatabaseConnectionPool databaseConnectionPool;

    public DatabaseHealthCheck(DatabaseConnectionPool databaseConnectionPool) {
        this.databaseConnectionPool = databaseConnectionPool;
    }

    @Override
    protected Result check() throws Exception {

        try (Connection connection = databaseConnectionPool.getConnection()) {
            if (connection.isValid(CONNECTION_TIMEOUT_IN_SECONDS)) {
                return Result.healthy();
            } else {
                return Result.unhealthy("Cannot connect to database");
            }
        }

    }
}