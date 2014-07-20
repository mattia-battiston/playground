package com.playground.jdbi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.ResultSetMapperFactory;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static org.fest.assertions.api.Assertions.assertThat;

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
        userDao.cleanUpDatabase();
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

    // TODO investigate more about automatic mapping
//    @Test
//    public void mapper() throws Exception {
//        dbi.registerMapper(new ResultSetMapper<User>() {
//            @Override
//            public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
//                User user = new User();
//                user.setName(r.getString("name"));
//                user.setUsername(r.getString("username"));
//                return user;
//            }
//        });
//
//        userDao.createTable();
//        userDao.insert("user1", "User One");
//        userDao.insert("user2", "User Two");
//
//        Collection<User> allUsers = userDao.findAllUsers();
//        assertThat(allUsers).hasSize(2);
//    }
}
