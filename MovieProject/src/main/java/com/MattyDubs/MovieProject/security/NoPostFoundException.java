package com.MattyDubs.MovieProject.security;

public class NoPostFoundException extends RuntimeException {
    public NoPostFoundException(String message) {
        super(message);
    }
}
