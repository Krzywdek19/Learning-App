package com.krzywdek19.learningApp.authentication;

import com.krzywdek19.learningApp.authentication.service.AuthenticationService;
import com.krzywdek19.learningApp.exception.NameIsTakenException;
import com.krzywdek19.learningApp.request.SignInRequest;
import com.krzywdek19.learningApp.request.SignUpRequest;
import com.krzywdek19.learningApp.response.ApiResponse;
import com.krzywdek19.learningApp.response.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> createUser(@RequestBody SignUpRequest signUpRequest){
        try{
            var user = authenticationService.signUp(signUpRequest);
            return ResponseEntity.ok(new ApiResponse("Successful registration", user));
        }catch (NameIsTakenException exception){
            return ResponseEntity.badRequest().body(new ApiResponse(exception.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignInRequest signIpRequest){
        return ResponseEntity.ok(authenticationService.signIn(signIpRequest));
    }
}
