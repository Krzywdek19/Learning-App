package com.krzywdek19.learningApp.user;

import com.krzywdek19.learningApp.learningKit.LearningKit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner")
    private List<LearningKit> learningKits;
}
