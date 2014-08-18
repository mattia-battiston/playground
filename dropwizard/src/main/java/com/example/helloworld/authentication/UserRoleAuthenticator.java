package com.example.helloworld.authentication;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class UserRoleAuthenticator implements Authenticator<BasicCredentials, User> {

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {

        if(!"secret".equals(credentials.getPassword()))
            return Optional.absent();

        if("role1".equals(credentials.getUsername()))
            return Optional.of(new User(credentials.getUsername(), UserRole.ROLE_1));

        if("role2".equals(credentials.getUsername()))
            return Optional.of(new User(credentials.getUsername(), UserRole.ROLE_2));

        if("admin".equals(credentials.getUsername()))
            return Optional.of(new User(credentials.getUsername(), UserRole.ADMIN));

        return Optional.absent();
    }

}
