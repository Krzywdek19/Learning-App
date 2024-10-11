package com.krzywdek19.learningApp.user;

import com.krzywdek19.learningApp.dto.UserDTO;
import com.krzywdek19.learningApp.request.SignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userRequestToUser(SignUpRequest request);
}
