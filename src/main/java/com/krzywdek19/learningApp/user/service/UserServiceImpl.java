package com.krzywdek19.learningApp.user.service;

import com.krzywdek19.learningApp.dto.UserDTO;
import com.krzywdek19.learningApp.exception.NameIsTakenEnum;
import com.krzywdek19.learningApp.exception.NameIsTakenException;
import com.krzywdek19.learningApp.request.SignUpRequest;
import com.krzywdek19.learningApp.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        if(userRepository.existsByEmail(request.email())){
            throw new NameIsTakenException(NameIsTakenEnum.EMAIL, request.email());
        }
        if(userRepository.existsByUsername(request.username())){
            throw new NameIsTakenException(NameIsTakenEnum.USERNAME, request.username());
        }
        return userMapper.userToUserDTO(
                userRepository.save(
                        User.builder()
                                .role(Role.USER)
                                .username(request.username())
                                .email(request.email())
                                .password(request.password())
                                .learningKits(new ArrayList<>())
                                .build()
                )
        );
    }
}
