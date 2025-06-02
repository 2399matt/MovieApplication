package com.MattyDubs.MovieProject.security;

import com.MattyDubs.MovieProject.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring-Security configuration. Configs for endpoint access along with the custom login-page,
 * landing page, and logout function.
 * Configures the JDBC user details manager to handle authentication in our DB.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyUserDetailsService userDetailsService(UserService userService) {
        return new MyUserDetailsService(userService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(config ->
                        config
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/movies/**").hasRole("USER")
                                .requestMatchers("/forum/**").hasRole("USER")
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(login ->
                        login.defaultSuccessUrl("/movies/home", true)
                                .loginPage("/register/login")
                                .loginProcessingUrl("/authenticateUser")
                                .permitAll()

                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(exc ->
                        exc.accessDeniedPage("/error")
                );


        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(MyUserDetailsService myUserDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}

