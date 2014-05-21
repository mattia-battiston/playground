package com.playground.springwebapp.customer;

public class CustomerRegistrationService {

    public void registerCustomer(Customer customer){
        System.out.println("Service is registering customer " + customer.getUsername());
    }

}
