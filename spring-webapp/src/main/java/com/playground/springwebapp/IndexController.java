package com.playground.springwebapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping(method = GET)
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("message", "Welcome to the example Spring web app!");
        return "welcome";
    }

}
