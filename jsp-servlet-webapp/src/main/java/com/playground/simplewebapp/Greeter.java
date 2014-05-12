package com.playground.simplewebapp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Greeter extends HttpServlet {

    public static final String USERNAME_PARAMETER = "username";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Greeting user...");

        String username = request.getParameter(USERNAME_PARAMETER);

        String result = "Hello " + username + "!";

        request.setAttribute("greetingMessage", result);

        RequestDispatcher dispatcher = request.getRequestDispatcher("greetingResult.jsp");
        dispatcher.forward(request, response);
    }

    public String greet(String name) {
        return "Hello " + name;
    }

}
