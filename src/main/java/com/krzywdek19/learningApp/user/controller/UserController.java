package com.krzywdek19.learningApp.user.controller;

import com.krzywdek19.learningApp.exception.IllegalInvokeException;
import com.krzywdek19.learningApp.exception.InvalidPasswordException;
import com.krzywdek19.learningApp.exception.NameIsTakenException;
import com.krzywdek19.learningApp.request.ChangePasswordRequest;
import com.krzywdek19.learningApp.request.ChangeUsernameRequest;
import com.krzywdek19.learningApp.response.ApiResponse;
import com.krzywdek19.learningApp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUser(){
        try {
            var user = userService.getCurrentUser();
            return ResponseEntity
                    .status(200)
                    .body(new ApiResponse("Current user", user));
        } catch (IllegalInvokeException e) {
            return handleExceptionCausedByUser(e);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getUsers(){
        return ResponseEntity
                .status(200)
                .body(new ApiResponse("All users", userService.findAllUsers()));
    }

    @PatchMapping("/username")
    public ResponseEntity<ApiResponse> changeUsername(@RequestBody ChangeUsernameRequest request){
        try {
            String jwt = userService.changeUsernameForCurrentUser(request);
            return ResponseEntity
                    .status(200)
                    .body(new ApiResponse("Username successfully changed", jwt));
        } catch (NameIsTakenException | IllegalInvokeException e) {
            return handleExceptionCausedByUser(e);
        }
    }


    @PatchMapping("/password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest request){
        try {
            String jwt = userService.changePasswordForCurrentUser(request);
            return ResponseEntity
                    .status(200)
                    .body(new ApiResponse("Password successfully changed",jwt));
        } catch (IllegalInvokeException | InvalidPasswordException e) {
            return handleExceptionCausedByUser(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteCurrentUser(@RequestBody String password){
        try {
            userService.deleteCurrentUser(password);
            return ResponseEntity
                    .status(204)
                    .body(new ApiResponse("Current user successfully deleted",null));
        } catch (IllegalInvokeException e) {
            return handleExceptionCausedByUser(e);
        }
    }

    private static ResponseEntity<ApiResponse> handleExceptionCausedByUser(Exception e) {
        return ResponseEntity
                .status(400)
                .body(new ApiResponse(e.getMessage(), null));
    }
}
