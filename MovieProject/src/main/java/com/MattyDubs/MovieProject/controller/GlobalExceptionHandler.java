package com.MattyDubs.MovieProject.controller;

import com.MattyDubs.MovieProject.security.NoPostFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    //TODO add exception handling/pages for specific exception cases.

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error";
    }

    @ExceptionHandler(RestClientResponseException.class)
    public String handleRestClientResponseException(RestClientResponseException e) {
        return "rest-error";
    }

    @ExceptionHandler(NoPostFoundException.class)
    public String handleNoPostFoundException(NoPostFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "no-post-found";
    }
}
