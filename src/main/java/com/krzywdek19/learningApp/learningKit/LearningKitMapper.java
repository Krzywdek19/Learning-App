package com.krzywdek19.learningApp.learningKit;

import com.krzywdek19.learningApp.dto.LearningKitDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LearningKitMapper {
    LearningKitDTO learningKitToLearningKitDTO(LearningKit learningKit);
}
