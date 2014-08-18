package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello-authentication")
@Produces(MediaType.APPLICATION_JSON)
public class HelloAuthenticationResource {

    @GET
    @Timed
    public String sayHello(@QueryParam("name") String name) {
        return "Hello authenticated " + name;
    }

}
