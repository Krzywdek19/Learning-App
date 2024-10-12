package com.krzywdek19.learningApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralApiResponse implements ApiResponse {
    private String message;
    private Object data;
}
