package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.WebUser;
import com.MattyDubs.MovieProject.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

/**
 * Registration service class used to build a webUser and CustomUser objects.
 */
@Service
public class RegistrationService {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(JdbcUserDetailsManager jdbcUserDetailsManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * registerUser method is used to create a new CustomUser and UserDetails object and save them to the database.
     *
     * @param webUser The webUser object filled in by the user.
     */
    public void registerUser(WebUser webUser) {
        CustomUser user = new CustomUser(webUser.getUsername());
        userService.save(user);
        UserDetails userDetails = User.builder()
                .username(webUser.getUsername())
                .password(passwordEncoder.encoder().encode(webUser.getPassword()))
                .roles("USER")
                .build();
        jdbcUserDetailsManager.createUser(userDetails);
    }
}
