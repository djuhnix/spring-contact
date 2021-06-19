package com.cnam.contact.controller;

import com.cnam.contact.bean.User;
import com.cnam.contact.exception.UserAlreadyExistException;
import com.cnam.contact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SecurityController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "security/registration";
    }

    @PostMapping("/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid User user,
            HttpServletRequest request,
            Errors errors
    ) {
        User newUser;
        try {
            newUser = userService.registerNewUserAccount(user);
        } catch (UserAlreadyExistException uaeEx) {
            ModelAndView mav = new ModelAndView("security/registration");
            mav.addObject("message", "Un compte existe déjà pour ce nom d'utilisateur.");
            return mav;
        }

        // rest of the implementation
        return new ModelAndView("dashboard", "user", newUser);
    }
}
