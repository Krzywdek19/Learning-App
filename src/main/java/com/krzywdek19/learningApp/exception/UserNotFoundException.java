package com.krzywdek19.learningApp.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User with username: " + username + " does not exist");
    }
}
