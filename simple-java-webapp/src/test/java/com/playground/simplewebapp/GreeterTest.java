package com.playground.simplewebapp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GreeterTest {

    @Test
    public void saysHello() throws Exception {
        Greeter greeter = new Greeter();

        String result = greeter.greet("Mattia");

        assertThat(result, is("Hello Mattia"));
    }
}