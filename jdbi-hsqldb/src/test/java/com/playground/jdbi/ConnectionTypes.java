package com.playground.jdbi;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class ConnectionTypes {

    public static final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";
    public static final String URL = "jdbc:hsqldb:http ://localhost/mydb";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

    @Test
    public void directConnection() throws Exception {
        Class.forName(DRIVER_CLASS_NAME);
        DBI dbi = new DBI(URL, USERNAME, PASSWORD);

        checkConnectionIsWorking(dbi);
    }

    @Test
    public void datasource() throws Exception {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(DRIVER_CLASS_NAME);
        basicDataSource.setUrl(URL);
        basicDataSource.setUsername(USERNAME);
        basicDataSource.setPassword(PASSWORD);
        DBI dbi = new DBI(basicDataSource);

        checkConnectionIsWorking(dbi);
    }

    @Test
    public void connectionFactory() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory() {
            @Override
            public Connection openConnection() throws SQLException {
                // this could be retrieving the connection from Spring, or run other integration code...
                try {
                    Class.forName("org.hsqldb.jdbcDriver");
                    return DriverManager.getConnection(URL, USERNAME, PASSWORD);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        };

        DBI dbi = new DBI(connectionFactory);

        checkConnectionIsWorking(dbi);
    }

    private void checkConnectionIsWorking(DBI dbi) {
        Handle h = dbi.open();

        h.execute("DROP SCHEMA PUBLIC CASCADE");
        h.execute("create table something (id int primary key, name varchar(100))");
        h.execute("insert into something (id, name) values (?, ?)", 1, "Mario");
        h.execute("insert into something (id, name) values (?, ?)", 2, "Luigi");

        List<Map<String, Object>> rs = h.select("select id, name from something");
        assertThat(rs).hasSize(2);

        h.close();
    }

}
