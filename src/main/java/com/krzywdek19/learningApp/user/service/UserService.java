package com.krzywdek19.learningApp.user.service;

import com.krzywdek19.learningApp.dto.UserDTO;
import com.krzywdek19.learningApp.exception.IllegalInvokeException;
import com.krzywdek19.learningApp.exception.NameIsTakenException;
import com.krzywdek19.learningApp.request.ChangePasswordRequest;
import com.krzywdek19.learningApp.request.ChangeUsernameRequest;
import com.krzywdek19.learningApp.request.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    UserDTO createUser(SignUpRequest request) throws NameIsTakenException;
    List<UserDTO> findAllUsers();
    UserDTO getCurrentUser() throws IllegalInvokeException;
    String changeUsernameForCurrentUser(ChangeUsernameRequest request) throws IllegalInvokeException, NameIsTakenException;
    String changePasswordForCurrentUser(ChangePasswordRequest request) throws IllegalInvokeException;
    void deleteCurrentUser(String password) throws IllegalInvokeException;
}
