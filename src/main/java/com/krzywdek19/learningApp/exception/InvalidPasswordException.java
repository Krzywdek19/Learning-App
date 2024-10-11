package com.krzywdek19.learningApp.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Password is incorrect");
    }
}
