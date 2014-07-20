package com.playground.jdbi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

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
        userDao.close();
    }

    @Test
    public void daoUsage() throws Exception {
        userDao.createTable();

        int numberOfInsertedRows = userDao.insert("mattia", "Mattia Battiston");
        assertThat(numberOfInsertedRows).isEqualTo(1);

        numberOfInsertedRows = userDao.insert("tony", "Tony Battiston");
        assertThat(numberOfInsertedRows).isEqualTo(1);

        assertThat(userDao.size()).isEqualTo(2);

        assertThat(userDao.findNameByUsername("mattia")).isEqualTo("Mattia Battiston");
        assertThat(userDao.findNameByUsername("tony")).isEqualTo("Tony Battiston");

        assertThat(userDao.findAllUsernames()).containsOnly("mattia", "tony");
    }

    private void registerDriver() throws ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
    }

    private void connect() {
        dbi = new DBI("jdbc:hsqldb:http ://localhost/mydb", "sa", "");

        // onDemand creates a dao that opens and closes the connection automatically as it needs to
        userDao = dbi.onDemand(UserDao.class);

        userDao.cleanUpDatabase();
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
