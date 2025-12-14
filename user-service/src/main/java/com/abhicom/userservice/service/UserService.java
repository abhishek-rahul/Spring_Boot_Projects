package com.abhicom.userservice.service;

import com.abhicom.userservice.dto.CreateUserRequest;
import com.abhicom.userservice.dto.UserResponse;
import com.abhicom.userservice.model.User;
import com.abhicom.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserResponse register(CreateUserRequest req) {
        // keeping it simple for Usecase A (no validation yet)
        if (req.getEmail() == null || req.getEmail().isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (repo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("email already exists");
        }

        String id = UUID.randomUUID().toString();
        User user = new User(id, req.getFirstName(), req.getLastName(), req.getEmail());
        repo.save(user);

        return toResponse(user);
    }

    public UserResponse getById(String id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + id));
        return toResponse(user);
    }

    public UserResponse getByEmail(String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("user not found with email: " + email));
        return toResponse(user);
    }

    private UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail());
    }
}
