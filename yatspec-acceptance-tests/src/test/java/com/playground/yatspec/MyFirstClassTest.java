package com.playground.yatspec;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.*;

public class MyFirstClassTest {

    MyFirstClass myFirstClass = new MyFirstClass();

    @Test
    public void greetsUser() throws Exception {
        assertThat(myFirstClass.greet("Mattia")).isEqualTo("Hello Mattia");
    }

}