package com.playground.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet resultSet, StatementContext context) throws SQLException {
        User user = new User();
        user.setUsername(resultSet.getString("username"));
        user.setName(resultSet.getString("name"));
        return user;
    }

}
