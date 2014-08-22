package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.authentication.RestrictedTo;
import com.example.helloworld.authentication.User;
import com.example.helloworld.authentication.UserRole;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-authentication")
@Produces(MediaType.APPLICATION_JSON)
public class HelloAuthenticationResource {

    @Path("role1")
    @GET
    @Timed
    public String sayHelloRole1(@RestrictedTo({UserRole.ROLE_1}) User user) {
        return "Hello authenticated " + user.getUsername();
    }

    @Path("role2")
    @GET
    @Timed
    public String sayHelloRole2(@RestrictedTo({UserRole.ROLE_2}) User user) {
        return "Hello authenticated " + user.getUsername();
    }

    @Path("noRole")
    @GET
    @Timed
    public String sayHelloNoRole(@Auth User user) {
        return "Hello authenticated " + user.getUsername();
    }

}
