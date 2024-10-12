package com.krzywdek19.learningApp.exception;

import com.krzywdek19.learningApp.response.ApiResponse;
import com.krzywdek19.learningApp.response.GeneralApiResponse;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {
    public static ResponseEntity<ApiResponse> handleExceptionCausedByUser(Exception e){
        return ResponseEntity
                .status(400)
                .body(new GeneralApiResponse(e.getMessage(), null));
    }
}
