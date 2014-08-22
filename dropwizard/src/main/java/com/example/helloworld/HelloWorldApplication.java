package com.example.helloworld;

import com.example.helloworld.authentication.ServerRestrictedToProvider;
import com.example.helloworld.authentication.User;
import com.example.helloworld.authentication.UserAuthenticator;
import com.example.helloworld.health.DatabaseHealthCheck;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.repositories.DatabaseConnectionPool;
import com.example.helloworld.resources.HelloAuthenticationResource;
import com.example.helloworld.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/web/views", "/views", "index.html", "views"));
        bootstrap.addBundle(new AssetsBundle("/web/css", "/css", null, "css"));
        bootstrap.addBundle(new AssetsBundle("/web/js", "/js", null, "js"));
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
        registerHelloWorldService(configuration, environment);

        registerExampleHealthCheck(configuration, environment);

        final DatabaseConnectionPool databaseConnectionPool = manageConnectionPool(environment);
        registerHealthCheckForConnectionPool(environment, databaseConnectionPool);

        registerBasicAuthAuthenticator(environment);
        registerCustomAuthenticator(environment);
        registerServiceThatRequiresAuthentication(environment);
    }

    private void registerHelloWorldService(HelloWorldConfiguration configuration, Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());
        environment.jersey().register(resource);
    }

    private void registerExampleHealthCheck(HelloWorldConfiguration configuration, Environment environment) {
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }

    private DatabaseConnectionPool manageConnectionPool(Environment environment) {
        final DatabaseConnectionPool databaseConnectionPool = new DatabaseConnectionPool();
        environment.lifecycle().manage(databaseConnectionPool);
        return databaseConnectionPool;
    }

    private void registerHealthCheckForConnectionPool(Environment environment, DatabaseConnectionPool databaseConnectionPool) {
        environment.healthChecks().register("database", new DatabaseHealthCheck(databaseConnectionPool));
    }

    private void registerCustomAuthenticator(Environment environment) {
        environment.jersey().register(new ServerRestrictedToProvider(new UserAuthenticator(), "myRealm"));
    }

    private void registerServiceThatRequiresAuthentication(Environment environment) {
        final HelloAuthenticationResource helloAuthenticationResource = new HelloAuthenticationResource();
        environment.jersey().register(helloAuthenticationResource);
    }

    private void registerBasicAuthAuthenticator(Environment environment) {
        BasicAuthProvider<User> basicAuthProvider = new BasicAuthProvider<>(new UserAuthenticator(), "myRealm");
        environment.jersey().register(basicAuthProvider);
    }

}