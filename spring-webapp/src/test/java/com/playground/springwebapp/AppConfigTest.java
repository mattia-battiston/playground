package com.playground.springwebapp;

import com.playground.springwebapp.customer.CustomerRegistrationService;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AppConfigTest {

    AppConfig appConfig = new AppConfig();

    @Test
    public void canCreateCustomerRegistrationService() throws Exception {
        assertThat(appConfig.customerRegistrationService()).isInstanceOf(CustomerRegistrationService.class);
    }
}
