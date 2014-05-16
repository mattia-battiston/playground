package com.playground.springwebapp;

import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationService {

    public void registerCustomer(Customer customer){
        System.out.println("Registering customer " + customer.getName() + " " + customer.getSurname());
    }

}
