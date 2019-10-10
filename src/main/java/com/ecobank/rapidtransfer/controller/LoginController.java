package com.ecobank.rapidtransfer.controller;

import com.ecobank.rapidtransfer.obj.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginView(){
        return "loginPage";
    }
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
    @PostMapping("/postLoginRecord")
    public String postLoginRecord(@ModelAttribute Login log, RedirectAttributes redirectAttributes){
        System.out.println("Username "+log.getUsername());
        System.out.println("Password "+log.getPassword());

        redirectAttributes.addFlashAttribute("name", log.getUsername());
        return "redirect:/welcome";
    }
}
