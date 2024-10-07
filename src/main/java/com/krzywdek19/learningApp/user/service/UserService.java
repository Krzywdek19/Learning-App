package com.krzywdek19.learningApp.user.service;

import com.krzywdek19.learningApp.dto.UserDTO;
import com.krzywdek19.learningApp.exception.NameIsTakenException;
import com.krzywdek19.learningApp.request.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    UserDTO createUser(SignUpRequest request) throws NameIsTakenException;
}
