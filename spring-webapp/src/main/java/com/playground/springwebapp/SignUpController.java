package com.playground.springwebapp;

import com.playground.springwebapp.customer.Customer;
import com.playground.springwebapp.customer.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/customer")
public class SignUpController {

    private CustomerRegistrationService customerRegistrationService;

    @Autowired
    public SignUpController(CustomerRegistrationService customerRegistrationService){
        this.customerRegistrationService = customerRegistrationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String displayCustomerForm(ModelMap model) {
        model.addAttribute("customer", new Customer());
        return "SignUpForm";

    }

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addCustomer(Customer customer, ModelMap model) {
        customerRegistrationService.registerCustomer(customer);
        model.addAttribute("registeredCustomer", customer);
		return "Done";
	}

}