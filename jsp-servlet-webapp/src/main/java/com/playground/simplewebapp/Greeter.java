package com.playground.simplewebapp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Greeter extends HttpServlet {

    public static final String USERNAME_PARAMETER = "username";

    public static final String GREETING_MESSAGE_PARAMETER = "greetingMessage";
    public static final String GREETING_RESULT_VIEW = "greetingResult.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Greeting user...");

        String username = request.getParameter(USERNAME_PARAMETER);

        String result = "Hello " + username + "!";

        request.setAttribute(GREETING_MESSAGE_PARAMETER, result);

        RequestDispatcher dispatcher = request.getRequestDispatcher(GREETING_RESULT_VIEW);
        dispatcher.forward(request, response);
    }

}
