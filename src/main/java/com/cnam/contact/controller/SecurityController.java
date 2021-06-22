package com.cnam.contact.controller;

import com.cnam.contact.bean.User;
import com.cnam.contact.exception.UserAlreadyExistException;
import com.cnam.contact.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SecurityController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "security/registration";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "security/access_denied";
    }

    @GetMapping("/logout")
    public String logout(
            HttpServletRequest request,
            Model model
    ) {
        try {
            request.logout();
        } catch (ServletException e) {
            model.addAttribute("message", "Echec de déconnexion.");
        }
        return "security/logout";
    }

    @GetMapping("/login")
    public String login(
            WebRequest request,
            @RequestParam(name = "error", required = false) boolean error,
            Model model
    ) {
        User user = new User();
        model.addAttribute("user", user);
        if (error) {
            model.addAttribute("message", "Echec de connexion.");
        }
        return "security/login";
    }

    @PostMapping("/login")
    public ModelAndView login(
            @ModelAttribute User user,
            HttpServletRequest request,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        try {
            request.login(user.getUsername(), passwordEncoder.encode(user.getPassword()));
        } catch (ServletException e) {
            ModelAndView mav = new ModelAndView("security/login");
            mav.addObject("message", "");
            return mav;
        }
        model.addAttribute("connexion", user);
        return new ModelAndView("home", "user", user);
    }

    @PostMapping("/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid User user,
            BindingResult result,
            HttpServletRequest request,
            Errors errors
    ) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("security/registration");
            mav.addObject("message", "Formulaire non valide.");
            return mav;
        }
        User newUser;
        System.out.println(user);
        try {
            newUser = userService.registerNewUserAccount(user);
        } catch (UserAlreadyExistException uaeEx) {
            ModelAndView mav = new ModelAndView("security/registration");
            mav.addObject("message", "Un compte existe déjà pour ce nom d'utilisateur.");
            return mav;
        }

        // rest of the implementation
        return new ModelAndView("redirect:contact", "user", newUser);
    }
}
