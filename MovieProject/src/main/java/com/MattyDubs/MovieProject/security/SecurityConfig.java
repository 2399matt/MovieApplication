package com.MattyDubs.MovieProject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Spring-Security configuration. Configs for endpoint access along with the custom login-page,
 * landing page, and logout function.
 * Configures the JDBC user details manager to handle authentication in our DB.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(config ->
                        config
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/movies/**").hasRole("USER")
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
                        exc.accessDeniedPage("/access-denied")
                );


        return http.build();
    }
}

