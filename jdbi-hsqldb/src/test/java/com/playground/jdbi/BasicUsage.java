package com.playground.jdbi;

import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;
import org.skife.jdbi.v2.util.StringMapper;

import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

public class BasicUsage {

    private Handle database;
    private DBI dbi;

    @Before
    public void setUp() throws Exception {
        registerDriver();

        connect();
    }

    private void registerDriver() throws ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
    }

    private void connect() {
        dbi = new DBI("jdbc:hsqldb:http ://localhost/mydb", "sa", "");
        database = dbi.open();
    }

    @Test
    public void basicUsage() throws Exception {
        database.execute(" create table something (" +
                "       id int primary key, " +
                "       name varchar(100)" +
                "   )");

        database.execute("insert into something (id, name) values (?, ?)", 1, "Brian");

        String name = database.createQuery("select name from something where id = :id")
                .bind("id", 1)
                .map(StringMapper.FIRST)
                .first();
        assertThat(name).isEqualTo("Brian");

        database.execute("update something set name = ? where id = ?", "New Brian", 1);

        String updatedName = database.createQuery("select name from something where id = :id").bind("id", 1).map(StringMapper.FIRST).first();
        assertThat(updatedName).isEqualTo("New Brian");

        database.close();
    }

    @Test
    public void selectList() throws Exception {
        database.execute(" create table users (" +
                "       username varchar(50) primary key, " +
                "       name varchar(100)" +
                "   )");

        database.insert("insert into users(username, name) values (?, ?)", "mattia", "Mattia Battiston");
        database.insert("insert into users(username, name) values (?, ?)", "tony", "Tony Battiston");

        List<Map<String, Object>> select = database.select("select * from users");
        System.out.println("select = " + select);

        List<Map<String, Object>> createQuery = database.createQuery("select * from users").list();
        System.out.println("createQuery = " + createQuery);

        List<User> users = database.createQuery("select * from users").map(User.class).list();
        System.out.println("users = " + users);

        database.close();
    }

    @Test
    public void withoutTheNeedToCloseIt() throws Exception {

        List<User> users = dbi.withHandle(new HandleCallback<List<User>>() {
            @Override
            public List<User> withHandle(Handle handle) throws Exception {
                database.execute(" create table users (" +
                        "       username varchar(50) primary key, " +
                        "       name varchar(100)" +
                        "   )");

                database.insert("insert into users(username, name) values (?, ?)", "mattia", "Mattia Battiston");
                database.insert("insert into users(username, name) values (?, ?)", "tony", "Tony Battiston");

                return database.createQuery("select * from users").map(User.class).list();
            }
        });

        assertThat(users).hasSize(2);
        assertThat(extractProperty("username").from(users)).containsOnly("mattia", "tony");
    }

}
