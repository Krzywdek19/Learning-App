package com.krzywdek19.learningApp.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignUpRequest{
    private String email;
    private String username;
    private String password;
}
