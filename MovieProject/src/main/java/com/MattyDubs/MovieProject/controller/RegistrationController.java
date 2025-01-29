package com.MattyDubs.MovieProject.controller;

import com.MattyDubs.MovieProject.dao.UserDAO;
import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.WebUser;
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

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    private UserService userService;

    /**
     * Mapping for the registration form.
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
     * @return the webpage for the login-form.
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    /**
     * Post-handling for adding a new user. We create the customUser from the model attribute that holds the
     * WebUser object. Then we add the user to Spring-Security using the UserDetails manager.
     * @param webUser The user object needed for our Spring-Security setup.
     * @return the login form, so that the new user can log in.
     */
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") WebUser webUser) {
        CustomUser customUser = new CustomUser(webUser.getUsername());
        userService.save(customUser);
        UserDetails user = User.builder()
                .username(webUser.getUsername())
                .password("{noop}" + webUser.getPassword())
                .roles("USER")
                .build();
        jdbcUserDetailsManager.createUser(user);
        return "login-form";
    }
}
