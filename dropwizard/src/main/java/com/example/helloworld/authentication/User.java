package com.example.helloworld.authentication;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class User {

    private String username;
    private Set<UserRole> roles = new HashSet<>();

    public User() {
    }

    public User(String username, UserRole... roles) {
        this.username = username;
        this.roles.addAll(asList(roles));
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }


}
