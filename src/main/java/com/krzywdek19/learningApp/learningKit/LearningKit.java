package com.krzywdek19.learningApp.learningKit;

import com.krzywdek19.learningApp.flashcards.Flashcard;
import com.krzywdek19.learningApp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class LearningKit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "kit")
    private List<Flashcard> flashcards;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
