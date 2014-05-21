package com.playground.springwebapp.index;

import com.playground.springwebapp.customer.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/index")
public class IndexController {

    @ModelAttribute("customer")
    public Customer getCustomerObject() {
        return new Customer();
    }

    @RequestMapping(method = GET)
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("message", "Welcome to the example Spring web app!");
        modelMap.addAttribute("customer", new Customer());
        return "index";
    }

}
