package com.krzywdek19.learningApp.user.service;

import com.krzywdek19.learningApp.dto.UserDTO;
import com.krzywdek19.learningApp.exception.*;
import com.krzywdek19.learningApp.jwt.JWTService;
import com.krzywdek19.learningApp.request.ChangePasswordRequest;
import com.krzywdek19.learningApp.request.ChangeUsernameRequest;
import com.krzywdek19.learningApp.request.SignUpRequest;
import com.krzywdek19.learningApp.user.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));
            }
        };
    }

    @Override
    public UserDTO createUser(SignUpRequest request) throws NameIsTakenException {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new NameIsTakenException(NameIsTakenEnum.EMAIL, request.getEmail());
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new NameIsTakenException(NameIsTakenEnum.USERNAME, request.getUsername());
        }
        return userMapper.userToUserDTO(
                userRepository.save(
                        User.builder()
                                .role(Role.USER)
                                .username(request.getUsername())
                                .email(request.getEmail())
                                .password(request.getPassword())
                                .learningKits(new ArrayList<>())
                                .build()
                )
        );
    }

    private String getCurrentUserUsername() throws IllegalInvokeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        throw new IllegalInvokeException("Method shouldn't be invoked without authenticated user");
    }

    private User getAuthenticatedUser() throws IllegalInvokeException {
        var username = getCurrentUserUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserDTO getCurrentUser() throws IllegalInvokeException {
        return userMapper.userToUserDTO(getAuthenticatedUser());
    }

    @Transactional
    @Override
    public String changeUsernameForCurrentUser(ChangeUsernameRequest request) throws IllegalInvokeException, NameIsTakenException {
        if(!userRepository.existsByUsername(request.username())) {
            var user = getAuthenticatedUser();
            if(passwordEncoder.matches(request.password(), user.getPassword())){
                user.setUsername(request.username());
                return jwtService.generateToken(user);
            }
            throw new InvalidPasswordException();
        }
        throw new NameIsTakenException(NameIsTakenEnum.USERNAME, request.username());
    }

    @Override
    public String changePasswordForCurrentUser(ChangePasswordRequest request) throws IllegalInvokeException {
        var user = getAuthenticatedUser();
        if(passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.newPassword()));
            return jwtService.generateToken(user);
        }
        throw new InvalidPasswordException();
    }

    @Override
    public void deleteCurrentUser(String password) throws IllegalInvokeException {
        var user = getAuthenticatedUser();
        if(passwordEncoder.matches(password, user.getPassword())){
            userRepository.delete(user);
        }
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .toList();
    }
}
