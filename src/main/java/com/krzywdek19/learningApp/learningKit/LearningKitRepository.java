package com.krzywdek19.learningApp.learningKit;

import com.krzywdek19.learningApp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningKitRepository extends JpaRepository<LearningKit, Long> {
    List<LearningKit> findAllByOwner(User owner);
}
