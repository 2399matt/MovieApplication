package com.MattyDubs.MovieProject.controller;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.WebUser;
import com.MattyDubs.MovieProject.security.PasswordEncoder;
import com.MattyDubs.MovieProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Registration controller responsible for endpoints related to authentication and the creation of new users.
 * Injects a password encoder, our user details manager, and a user service object for handling user creation.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final PasswordEncoder encoder;

    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    private final UserService userService;

    @Autowired
    public RegistrationController(PasswordEncoder encoder, JdbcUserDetailsManager jdbcUserDetailsManager, UserService userService) {
        this.encoder = encoder;
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.userService = userService;
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
        model.addAttribute("user", new WebUser());
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
     * @param webUser The user entity needed for our Spring-Security setup.
     * @return the login form, so that the new user can log in.
     */
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") WebUser webUser) {
        CustomUser customUser = new CustomUser(webUser.getUsername());
        userService.save(customUser);
        UserDetails user = User.builder()
                .username(webUser.getUsername())
                .password(encoder.encoder().encode(webUser.getPassword()))
                .roles("USER")
                .build();
        jdbcUserDetailsManager.createUser(user);
        return "login-form";
    }
}
