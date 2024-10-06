package com.krzywdek19.learningApp.authentication;

import com.krzywdek19.learningApp.exception.NameIsTakenException;
import com.krzywdek19.learningApp.request.CreateUserRequest;
import com.krzywdek19.learningApp.response.ApiResponse;
import com.krzywdek19.learningApp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
        try{
            var user = userService.createUser(createUserRequest);
            return ResponseEntity.ok(new ApiResponse("User created", user));
        }catch (NameIsTakenException exception){
            return ResponseEntity.badRequest().body(new ApiResponse(exception.getMessage(), null));
        }
    }
}
