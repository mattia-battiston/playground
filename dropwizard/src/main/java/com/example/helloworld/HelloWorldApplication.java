package com.example.helloworld;

import com.example.helloworld.authentication.ServerRestrictedToProvider;
import com.example.helloworld.authentication.UserRoleAuthenticator;
import com.example.helloworld.health.DatabaseHealthCheck;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.repositories.DatabaseConnectionPool;
import com.example.helloworld.resources.HelloAuthenticationResource;
import com.example.helloworld.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/web/views", "/views", "index.htm", "views"));
        bootstrap.addBundle(new AssetsBundle("/web/css", "/css", null, "css"));
        bootstrap.addBundle(new AssetsBundle("/web/js", "/js", null, "js"));
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());
        environment.jersey().register(resource);

        final HelloAuthenticationResource helloAuthenticationResource = new HelloAuthenticationResource();
        environment.jersey().register(helloAuthenticationResource);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        final DatabaseConnectionPool databaseConnectionPool = new DatabaseConnectionPool();
        environment.lifecycle().manage(databaseConnectionPool);
        environment.healthChecks().register("database", new DatabaseHealthCheck(databaseConnectionPool));

//        BasicAuthProvider<User> basicAuthProvider = new BasicAuthProvider<>(new SimpleAuthenticator(), "myRealm");
//        BasicAuthProvider<User> basicAuthProvider = new BasicAuthProvider<>(new UserRoleAuthenticator(), "myRealm");
//        environment.jersey().register(basicAuthProvider);

        environment.jersey().register(new ServerRestrictedToProvider(new UserRoleAuthenticator(), "myRealm"));
    }

}