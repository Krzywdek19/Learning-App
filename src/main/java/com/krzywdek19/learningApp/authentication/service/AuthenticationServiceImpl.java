package com.krzywdek19.learningApp.authentication.service;

import com.krzywdek19.learningApp.dto.UserDTO;
import com.krzywdek19.learningApp.exception.NameIsTakenException;
import com.krzywdek19.learningApp.jwt.JWTService;
import com.krzywdek19.learningApp.request.SignInRequest;
import com.krzywdek19.learningApp.request.SignUpRequest;
import com.krzywdek19.learningApp.response.JwtAuthenticationResponse;
import com.krzywdek19.learningApp.exception.UserNotFoundException;
import com.krzywdek19.learningApp.user.UserRepository;
import com.krzywdek19.learningApp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    public UserDTO signUp(SignUpRequest request) throws NameIsTakenException {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userService.createUser(request);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        var user = userRepository.findByUsername(request.username()).orElseThrow(()-> new UserNotFoundException(request.username()));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return new JwtAuthenticationResponse(jwt, refreshToken);
    }
}
