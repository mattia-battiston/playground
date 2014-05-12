package com.playground.simplewebapp;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.playground.simplewebapp.Greeter.GREETING_MESSAGE_PARAMETER;
import static com.playground.simplewebapp.Greeter.GREETING_RESULT_VIEW;
import static com.playground.simplewebapp.Greeter.USERNAME_PARAMETER;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GreeterTest {

    Greeter greeter = new Greeter();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Test
    public void forwardsToTheGreetingsPage() throws ServletException, IOException {
        givenARequestIsComingInForUser("Mattia");

        whenTheRequestIsReceived();

        thenTheRequestIsForwardedToTheGreetingsPage();
    }

    @Test
    public void greetsUserOnTheGreetingsPage() throws Exception {
        givenARequestIsComingInForUser("Mattia");

        whenTheRequestIsReceived();

        thenTheGreetinsPageWillGreetTheUserWithMessage("Hello Mattia!");
    }

    private void givenARequestIsComingInForUser(String username) {
        when(request.getRequestDispatcher(GREETING_RESULT_VIEW)).thenReturn(requestDispatcher);
        when(request.getParameter(USERNAME_PARAMETER)).thenReturn(username);
    }

    private void whenTheRequestIsReceived() throws ServletException, IOException {
        greeter.doGet(request, response);
    }

    private void thenTheRequestIsForwardedToTheGreetingsPage() throws ServletException, IOException {
        verify(requestDispatcher).forward(request, response);
    }

    private void thenTheGreetinsPageWillGreetTheUserWithMessage(String expectedGreetingMessage) {
        verify(request).setAttribute(GREETING_MESSAGE_PARAMETER, expectedGreetingMessage);
    }

}