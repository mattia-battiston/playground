package com.playground.springwebapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomerServiceRest {

    @RequestMapping("/rest/customer")
    public @ResponseBody Customer customer(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname("unknown");
        customer.setAge(100);
        return customer;
    }

}
