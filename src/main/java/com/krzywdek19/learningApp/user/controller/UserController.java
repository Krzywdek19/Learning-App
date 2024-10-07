package com.krzywdek19.learningApp.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping
    public ResponseEntity<?> sayHello(){
        return ResponseEntity.ok("Hello " + SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
