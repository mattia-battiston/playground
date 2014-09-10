package com.mattia.java8lambdas.chapter2;

import org.junit.Test;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class Answers {

    @Test
    public void question1() {
        //a. T -> Function -> R
        //b. something that operates on one input, e.g. not
        //c.
        // x -> x + 1; yes
        // (x, y) -> x + 1; no, should take only one input
        // x -> x ==1; no, should return a Long
    }

    @Test
    public void question2() {
        ThreadLocal<DateFormat> formatterThreadLocal = ThreadLocal.withInitial( () -> new SimpleDateFormat("dd-MMM-yyyy") );

        String formattedDate = formatterThreadLocal.get().format(new Date());

        System.out.println("formattedDate = " + formattedDate);
    }

    @Test
    public void question3() throws Exception {

        // a. yes
        Runnable runnable = () -> System.out.println("Hello world");

    }
}
