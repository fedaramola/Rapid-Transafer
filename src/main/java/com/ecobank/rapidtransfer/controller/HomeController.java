package com.ecobank.rapidtransfer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @GetMapping("/home")
   // @RequestMapping(method = RequestMethod.GET, value = "/")
    public String home(Model model){
        model.addAttribute("name", "Joel");



        return "index";
    }
}
