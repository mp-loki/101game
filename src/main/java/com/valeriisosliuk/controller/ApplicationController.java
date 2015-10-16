package com.valeriisosliuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.valeriisosliuk.service.UserService;

@Controller
public class ApplicationController {

	@Autowired
    private UserService userService;
    
    @RequestMapping(value = "/")
    public String root(Model model) {
        return "index";
    }
    
    @RequestMapping(value = "/index")
    public String index(Model model) {
        return "index";
    }
    
    @RequestMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/home")
    public String game(Model model) {
        model.addAttribute("name", userService.getCurrentUserName());
        model.addAttribute("users", userService.getLoggedInUserNames());
        return "home";
    }
}
