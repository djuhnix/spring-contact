package com.cnam.contact.controller;

import com.cnam.contact.bean.User;
import org.apache.tomcat.util.modeler.BaseAttributeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String index(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "index";}
}
