package com.playground.jdbi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.ResultSetMapperFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

public class DaoUsage {


    private UserDao userDao;
    private DBI dbi;

    @Before
    public void setUp() throws Exception {
        registerDriver();

        connect();
    }

    @After
    public void tearDown() throws Exception {
        userDao.close();
    }

    private void registerDriver() throws ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
    }

    private void connect() {
        dbi = new DBI("jdbc:hsqldb:http ://localhost/mydb", "sa", "");
        userDao = dbi.open(UserDao.class);
    }

    @Test
    public void daoUsage() throws Exception {
        userDao.createTable();

        int numberOfInsertedRows = userDao.insert("mattia", "Mattia Battiston");
        assertThat(numberOfInsertedRows).isEqualTo(1);

        numberOfInsertedRows = userDao.insert("tony", "Tony Battiston");
        assertThat(numberOfInsertedRows).isEqualTo(1);

        assertThat(userDao.findNameById("mattia")).isEqualTo("Mattia Battiston");
        assertThat(userDao.findNameById("tony")).isEqualTo("Tony Battiston");
    }

    @Test
    public void insertBean() throws Exception {
        userDao.createTable();

        userDao.insertUser(new User("mattia", "Mattia Battiston"));

        assertThat(userDao.size()).isEqualTo(1);
    }

}
