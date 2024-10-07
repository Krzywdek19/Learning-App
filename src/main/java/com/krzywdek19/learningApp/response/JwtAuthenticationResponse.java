package com.krzywdek19.learningApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
}
