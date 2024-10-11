package com.krzywdek19.learningApp.authentication.service;

import com.krzywdek19.learningApp.dto.UserDTO;
import com.krzywdek19.learningApp.exception.NameIsTakenException;
import com.krzywdek19.learningApp.request.SignInRequest;
import com.krzywdek19.learningApp.request.SignUpRequest;
import com.krzywdek19.learningApp.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    UserDTO signUp(SignUpRequest request) throws NameIsTakenException;
    JwtAuthenticationResponse signIn(SignInRequest request);
}
