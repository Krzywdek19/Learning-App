package com.krzywdek19.learningApp.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class type, long id) {
        super("Could not find resource " + type + " with id " + id);
    }
}
