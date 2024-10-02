package com.krzywdek19.learningApp.flashcards;

import com.krzywdek19.learningApp.learningKit.LearningKit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String definition;
    @ManyToOne
    @JoinColumn(name = "learning_kit_id")
    private LearningKit kit;
}
