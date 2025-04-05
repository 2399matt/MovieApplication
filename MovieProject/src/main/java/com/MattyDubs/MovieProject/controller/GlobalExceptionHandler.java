package com.MattyDubs.MovieProject.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //TODO add exception handling/pages for specific exception cases.

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error";
    }
}
