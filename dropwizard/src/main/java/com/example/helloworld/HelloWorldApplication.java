package com.example.helloworld;

import com.example.helloworld.health.DatabaseHealthCheck;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.repositories.DatabaseConnectionPool;
import com.example.helloworld.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());
        environment.jersey().register(resource);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        final DatabaseConnectionPool databaseConnectionPool = new DatabaseConnectionPool();
        environment.lifecycle().manage(databaseConnectionPool);
        environment.healthChecks().register("database", new DatabaseHealthCheck(databaseConnectionPool));
    }

}