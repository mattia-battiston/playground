package com.playground.springwebapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@ComponentScan(basePackages = {"com.playground.springwebapp"})
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public MyService myService() {
        return new MyService();
    }

}
