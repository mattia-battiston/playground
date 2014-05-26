package com.playground.jdbi;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Collection;

public interface UserDao {

    @SqlUpdate(" create table users (" +
            "       username varchar(50) primary key, " +
            "       name varchar(100)" +
            "   )")
    void createTable();

    @SqlUpdate("insert into users (username, name) values (:username, :name)")
    int insert(@Bind("username") String username, @Bind("name") String name);

    @SqlQuery("select name from users where username = :username")
    String findNameById(@Bind("username") String username);

    @SqlQuery("select * from users")
    Collection<User> findAllUsers();

    /**
     * close with no args is used to close the connection
     */
    void close();

}
