package com.playground.springwebapp.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.ws.Action;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/registerCustomer")
public class RegisterCustomerController {

    private CustomerRegistrationService customerRegistrationService;

    @Autowired
    public RegisterCustomerController(CustomerRegistrationService customerRegistrationService) {
        this.customerRegistrationService = customerRegistrationService;
    }

    @RequestMapping(method = GET)
    public ModelAndView registerCustomer(@ModelAttribute("customer") Customer customer) {
        System.out.println("Registering customer..." + customer.getUsername());

        customerRegistrationService.registerCustomer(customer);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("registeredCustomer", customer.getUsername());
        modelAndView.setViewName("customerRegistered");
        return modelAndView;
    }

}
