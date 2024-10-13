package com.krzywdek19.learningApp.learningKit.controller;

import com.krzywdek19.learningApp.exception.ExceptionHandler;
import com.krzywdek19.learningApp.exception.IllegalInvokeException;
import com.krzywdek19.learningApp.exception.ResourceNotFoundException;
import com.krzywdek19.learningApp.learningKit.service.LearningKitService;
import com.krzywdek19.learningApp.request.ConfirmPasswordRequest;
import com.krzywdek19.learningApp.request.CreateLearningKitRequest;
import com.krzywdek19.learningApp.request.UpdateLearningKitRequest;
import com.krzywdek19.learningApp.response.ApiResponse;
import com.krzywdek19.learningApp.response.CreateResourceResponse;
import com.krzywdek19.learningApp.response.GeneralApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Controller
@RequestMapping("/api/v1/learningKit")
@RequiredArgsConstructor
public class LearningKitController {
    private final LearningKitService learningKitService;

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse> getLearningKitById(@PathVariable("id") Long id) {
        try {
            var learningKit = learningKitService.getLearningKitById(id);
            return ResponseEntity
                    .status(200)
                    .body(new GeneralApiResponse("Getting learning kit", learningKit));
        } catch (Exception e) {
            return ExceptionHandler.handleExceptionCausedByUser(e);
        }
    }

    @GetMapping
    ResponseEntity<ApiResponse> getCurrentUserLearningKits(){
        try {
            var learningKits = learningKitService.getCurrentUserLearningKits();
            return ResponseEntity
                    .status(200)
                    .body(new GeneralApiResponse("Current user learning kits", learningKits));
        } catch (IllegalInvokeException e) {
            return ExceptionHandler.handleExceptionCausedByUser(e);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createLearningKit(@RequestBody CreateLearningKitRequest request){
        try {
            var learningKitUrl = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(learningKitService.createLearningKit(request).id());
            return ResponseEntity
                    .status(201)
                    .body(new CreateResourceResponse("Learning kit has been created", new URI(Objects.requireNonNull(learningKitUrl.getPath()))));
        }catch (IllegalInvokeException e){
            return ExceptionHandler.handleExceptionCausedByUser(e);
        } catch (URISyntaxException e) {
            return ResponseEntity
                    .status(500)
                    .body(new GeneralApiResponse("Error creating learning kit url", e.getMessage()));
        }
    }


    @PatchMapping("/{id}")
    ResponseEntity<ApiResponse> updateLearningKit(@RequestBody UpdateLearningKitRequest request, @PathVariable Long id){
        try {
            var updatedLearningKit = learningKitService.changeLearningKitTitle(request, id);
            return ResponseEntity
                    .status(200)
                    .body(new GeneralApiResponse("Title has been changed successfully", updatedLearningKit));
        }catch (ResourceNotFoundException e){
            return ExceptionHandler.handleExceptionCausedByUser(e);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteLearningKit(@PathVariable Long id, @RequestBody ConfirmPasswordRequest request){
        try {
            learningKitService.deleteLearningKitById(id, request.password());
            return ResponseEntity
                    .status(204)
                    .build();
        }catch (Exception e){
            return ExceptionHandler.handleExceptionCausedByUser(e);
        }
    }
}
