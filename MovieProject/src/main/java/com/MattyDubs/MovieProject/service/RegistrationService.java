package com.MattyDubs.MovieProject.service;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Registration service class used to build a webUser and CustomUser objects.
 */
@Service
public class RegistrationService {


    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * registerUser method is used to create a new CustomUser and UserDetails object and save them to the database.
     *
     * @param user The CustomUser object filled in by the user.
     */
    public void registerUser(CustomUser user) {
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
    }
}
