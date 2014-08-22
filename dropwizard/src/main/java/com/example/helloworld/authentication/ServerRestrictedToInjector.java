package com.example.helloworld.authentication;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.StringUtil;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServerRestrictedToInjector<T> extends AbstractHttpContextInjectable<T> {

    private final UserAuthenticator userRoleAuthenticator;
    private final String realm;
    private final UserRole value;

    public ServerRestrictedToInjector(UserAuthenticator userRoleAuthenticator, String realm, UserRole[] value) {
        this.userRoleAuthenticator = userRoleAuthenticator;
        this.realm = realm;
        this.value = value[0];
    }

    @Override
    public T getValue(HttpContext httpContext) {
        try {

            // Get the Authorization header
            final String header = httpContext.getRequest().getHeaderValue(HttpHeaders.AUTHORIZATION);
            if (header != null) {

                System.out.println("Authorization header: " + header);

                final int space = header.indexOf(' ');
                final String decoded = B64Code.decode(header.substring(space + 1), StringUtil.__ISO_8859_1);
                final int colonIndex = decoded.indexOf(':');
                final String username = decoded.substring(0, colonIndex);
                final String password = decoded.substring(colonIndex + 1);
                BasicCredentials credentials = new BasicCredentials(username, password);

                final Optional<User> result = userRoleAuthenticator.authenticate(credentials);
                if (result.isPresent()) {
                    User user = result.get();
                    if(user.hasRole(value)) {
                        return (T) result.get();
                    } else {
                        // user does not have permissions
                        throw new WebApplicationException(
                                Response.status(Response.Status.UNAUTHORIZED)
                                        .header(HttpHeaders.AUTHORIZATION, realm)
                                        .entity("Credentials are required to access this resource.")
                                        .type(MediaType.TEXT_PLAIN_TYPE)
                                        .build());
                    }
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
