package com.MattyDubs.MovieProject.controller;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.event.RegistrationEvent;
import com.MattyDubs.MovieProject.service.RegistrationService;
import com.MattyDubs.MovieProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Registration controller responsible for endpoints related to authentication and the creation of new users.
 * Injects a password encoder, our user details manager, and a user service object for handling user creation.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {
    //TODO USERNAME AND MOVIES CHECK SHOULD USE A COUNT QUERY.

    private final RegistrationService registrationService;
    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public RegistrationController(RegistrationService registrationService, UserService userService, ApplicationEventPublisher publisher) {
        this.registrationService = registrationService;
        this.userService = userService;
        this.publisher = publisher;
    }

    /**
     * Mapping for the registration form. Adds a webUser object to the model, which the form will pass to
     * the AddUser method to create a customUser and Spring Security user.
     *
     * @param model The model that the controller uses to pass information to the webpage.
     * @return the registration form for new users.
     */
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("user", new CustomUser());
        return "reg-form";
    }

    /**
     * The login endpoint, configured in Spring-Security to be the default page for login.
     *
     * @return the webpage for the login-form.
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    /**
     * Post-handling for adding a new user. We create the customUser from the model attribute that holds the
     * WebUser object. Then we build a UserDetails object for Spring Security to use.
     *
     * @param user The user entity needed for our Spring-Security setup.
     * @return the login form, so that the new user can log in.
     */
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") CustomUser user, @RequestParam("passCheck") String password, Model model) {
        if (!userService.checkUserExists(user.getUsername())) {
            model.addAttribute("userError", "username already exists.");
            return "reg-form";
        }
        if (!user.getPassword().equals(password)) {
            model.addAttribute("passError", "passwords do not match.");
            return "reg-form";
        }
        registrationService.registerUser(user);
        publisher.publishEvent(new RegistrationEvent(user));
        return "login-form";
    }
}
