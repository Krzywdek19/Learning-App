package com.krzywdek19.learningApp.learningKit.service;

import com.krzywdek19.learningApp.dto.LearningKitDTO;
import com.krzywdek19.learningApp.exception.IllegalInvokeException;
import com.krzywdek19.learningApp.request.CreateLearningKitRequest;
import com.krzywdek19.learningApp.request.UpdateLearningKitRequest;

import java.util.List;

public interface LearningKitService {
    LearningKitDTO getLearningKitById(long id);
    List<LearningKitDTO> getAllLearningKits();
    List<LearningKitDTO> getCurrentUserLearningKits() throws IllegalInvokeException;
    LearningKitDTO createLearningKit(CreateLearningKitRequest request) throws IllegalInvokeException;
    LearningKitDTO changeLearningKitTitle(UpdateLearningKitRequest request, long id);
    void deleteLearningKitById(long id, String password) throws IllegalInvokeException;
}
