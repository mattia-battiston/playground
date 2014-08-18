package com.example.helloworld.authentication;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServerRestrictedToInjector<T> extends AbstractHttpContextInjectable<T> {

    private final UserRoleAuthenticator userRoleAuthenticator;
    private final String realm;
    private final UserRole[] value;

    public ServerRestrictedToInjector(UserRoleAuthenticator userRoleAuthenticator, String realm, UserRole[] value) {
        this.userRoleAuthenticator = userRoleAuthenticator;
        this.realm = realm;
        this.value = value;
    }

    @Override
    public T getValue(HttpContext httpContext) {
        try {

            // Get the Authorization header
            final String header = httpContext.getRequest().getHeaderValue(HttpHeaders.AUTHORIZATION);
            if (header != null) {

                System.out.println("Authorization header: " + header);

                // TODO decript basic auth header
                BasicCredentials credentials = new BasicCredentials("role1", "secret");

                final Optional<User> result = userRoleAuthenticator.authenticate(credentials);
                if (result.isPresent()) {
                    return (T) result.get();
                }
            } else {
                System.out.println("No authorization header!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error decoding credentials");
        } catch (AuthenticationException e) {
            System.out.println("Error authenticating credentials");
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }

        // Must have failed to be here
        throw new WebApplicationException(
                Response.status(Response.Status.UNAUTHORIZED)
                    .header(HttpHeaders.AUTHORIZATION, realm)
                    .entity("Credentials are required to access this resource.")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build());
    }

}
