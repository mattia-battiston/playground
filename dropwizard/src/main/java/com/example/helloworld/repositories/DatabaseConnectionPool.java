package com.example.helloworld.repositories;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionPool implements Managed {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConnectionPool.class);

    private static final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";
    private static final String URL = "jdbc:hsqldb:http ://localhost/mydb";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private BoneCP connectionPool;

    @Override
    public void start() throws Exception {
        log.info("Initialising database connection pool...");
        Class.forName(DRIVER_CLASS_NAME);
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setInitSQL("SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS");

        connectionPool = new BoneCP(config);
        log.info("Initialised database connection pool!");
    }

    @Override
    public void stop() throws Exception {
        log.info("Closing database connection pool...");
        connectionPool.shutdown();
        log.info("Closed database connection pool!");
    }

    public Connection getConnection() {
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
