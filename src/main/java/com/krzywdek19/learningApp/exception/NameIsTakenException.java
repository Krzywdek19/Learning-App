package com.krzywdek19.learningApp.exception;

public class NameIsTakenException extends Exception{
    public NameIsTakenException(NameIsTakenEnum type, String value) {
        super((type.name().equals("EMAIL")? "Email  " : "Username ") + " is already taken: " + value);
    }
}
