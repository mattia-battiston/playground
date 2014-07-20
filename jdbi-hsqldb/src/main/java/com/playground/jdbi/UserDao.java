package com.playground.jdbi;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Iterator;
import java.util.List;

@RegisterMapper(UserMapper.class)
public interface UserDao {

    @SqlUpdate(
            " create table users (" +
            "       username varchar(50) primary key, " +
            "       name varchar(100)" +
            "   )")
    void createTable();

    @SqlUpdate("insert into users (username, name) values (:username, :name)")
    int insert(@Bind("username") String username, @Bind("name") String name);

    @SqlQuery("select name from users where username = :username")
    String findNameByUsername(@Bind("username") String username);

    @SqlQuery("select username from users")
    List<String> findAllUsernames();

    @SqlQuery("select username from users")
    Iterator<String> lazilyFindAllUsernames();

    @SqlQuery("select count(*) from users")
    Integer size();

    @SqlQuery("select * from users")
    List<User> findAllUsers();

    /**
     * close with no args is used to close the connection
     */
    void close();

    @SqlUpdate(
            "DROP SCHEMA PUBLIC CASCADE"
    )
    void cleanUpDatabase();

}
