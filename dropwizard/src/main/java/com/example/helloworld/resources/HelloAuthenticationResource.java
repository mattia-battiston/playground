package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.authentication.User;
import io.dropwizard.auth.Auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-authentication")
@Produces(MediaType.APPLICATION_JSON)
public class HelloAuthenticationResource {

    @GET
    @Timed
    @Consumes("application/json")
    public String sayHello(@Auth User user) {
        return "Hello authenticated " + user.getUsername();
    }

}
