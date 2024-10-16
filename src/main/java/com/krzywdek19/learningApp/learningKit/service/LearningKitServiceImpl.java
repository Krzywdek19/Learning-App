package com.krzywdek19.learningApp.learningKit.service;

import com.krzywdek19.learningApp.dto.LearningKitDTO;
import com.krzywdek19.learningApp.exception.IllegalInvokeException;
import com.krzywdek19.learningApp.exception.InvalidPasswordException;
import com.krzywdek19.learningApp.exception.ResourceNotFoundException;
import com.krzywdek19.learningApp.learningKit.LearningKit;
import com.krzywdek19.learningApp.learningKit.LearningKitMapper;
import com.krzywdek19.learningApp.learningKit.LearningKitRepository;
import com.krzywdek19.learningApp.request.CreateLearningKitRequest;
import com.krzywdek19.learningApp.request.UpdateLearningKitRequest;
import com.krzywdek19.learningApp.user.User;
import com.krzywdek19.learningApp.user.UserRepository;
import com.krzywdek19.learningApp.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LearningKitServiceImpl implements LearningKitService {
    private final LearningKitRepository learningKitRepository;
    private final LearningKitMapper learningKitMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User getCurrentUser() throws IllegalInvokeException {
        return userRepository
                .findByUsername(userService
                        .getCurrentUser()
                        .username())
                .orElseThrow(() -> new IllegalInvokeException("Unauthenticated user"));
    }

    private boolean belongsToCurrentUser(User user, LearningKit learningKit) throws IllegalInvokeException {
        return Objects.equals(learningKit.getOwner().getId(), getCurrentUser().getId());
    }

    @Override
    public LearningKitDTO getLearningKitById(long id) throws IllegalInvokeException {
        var learningKit = learningKitRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(LearningKit.class, id));
        if(belongsToCurrentUser(getCurrentUser(), learningKit)){
            return learningKitMapper
                    .learningKitToLearningKitDTO(learningKit);
        }
        throw new IllegalInvokeException("Unauthenticated access");
    }

    @Override
    public List<LearningKitDTO> getAllLearningKits() {
        return learningKitRepository
                .findAll()
                .stream()
                .map(learningKitMapper::learningKitToLearningKitDTO)
                .toList();
    }

    @Override
    public List<LearningKitDTO> getCurrentUserLearningKits() throws IllegalInvokeException {
        return learningKitRepository
                .findAllByOwner(getCurrentUser())
                .stream()
                .map(learningKitMapper::learningKitToLearningKitDTO)
                .toList();
    }

    @Override
    public LearningKitDTO createLearningKit(CreateLearningKitRequest request) throws IllegalInvokeException {
        var owner = getCurrentUser();
        var learningKit = LearningKit
                .builder()
                .title(request.title())
                .owner(owner)
                .flashcards(new ArrayList<>())
                .build();
        return learningKitMapper
                .learningKitToLearningKitDTO(learningKitRepository.save(learningKit));
    }

    @Override
    @Transactional
    public LearningKitDTO changeLearningKitTitle(UpdateLearningKitRequest request, long id) {
        var learningKit = learningKitRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LearningKit.class, id));
        learningKit.setTitle(request.title());
        return learningKitMapper.learningKitToLearningKitDTO(learningKit);
    }

    @Override
    public void deleteLearningKitById(long id, String password) throws IllegalInvokeException {
        var learningKit = learningKitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(LearningKit.class, id));
        if(belongsToCurrentUser(getCurrentUser(), learningKit)){
            if(passwordEncoder.matches(password, getCurrentUser().getPassword())){
                learningKitRepository.delete(learningKit);
                return;
            }
            throw new InvalidPasswordException();
        }
        throw new IllegalInvokeException("Unauthenticated access");
    }
}
