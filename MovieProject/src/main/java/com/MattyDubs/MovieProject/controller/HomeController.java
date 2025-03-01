package com.MattyDubs.MovieProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Home controller that is used to redirect the user from the default url
 * Sends the user to the login page, which has a default success url setup.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/register/login";
    }
}
