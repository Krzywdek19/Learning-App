package com.krzywdek19.learningApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.util.UriComponents;

import java.net.URI;

@AllArgsConstructor
@Data
public class CreateResourceResponse implements ApiResponse {
    private String message;
    private URI url;
}
