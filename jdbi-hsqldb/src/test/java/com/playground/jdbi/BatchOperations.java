package com.playground.jdbi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.util.HashSet;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

public class BatchOperations {

    public static final int NUMBER_OF_ROWS = 100;

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

    @Test
    public void batchInsert() throws Exception {
        userDao.createTable();

        Set<User> users = new HashSet<>();
        for(int i = 1; i <= 100; i++){
            User user = new User();
            user.setUsername("username" + i);
            user.setName("User Name " + i);
            users.add(user);
        }

        userDao.bulkInsert(users);

        Integer size = userDao.size();
        assertThat(size).isEqualTo(NUMBER_OF_ROWS);
    }

    private void registerDriver() throws ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
    }

    private void connect() {
        dbi = new DBI("jdbc:hsqldb:http ://localhost/mydb", "sa", "");
        userDao = dbi.onDemand(UserDao.class);
        userDao.cleanUpDatabase();
    }


}
