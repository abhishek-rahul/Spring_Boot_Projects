package com.abhicom.userservice.service;

import com.abhicom.userservice.dto.CreateUserRequest;
import com.abhicom.userservice.dto.UserResponse;
import com.abhicom.userservice.exception.BadRequestException;
import com.abhicom.userservice.exception.NotFoundException;
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

    public UserResponse createUser(CreateUserRequest req) {
        // validation is already handled by @Valid, so don’t re-check blanks here

        if (repo.existsByEmail(req.getEmail())) {
            throw new BadRequestException("email already exists");
        }

        String id = UUID.randomUUID().toString();
        User user = new User(id, req.getFirstName(), req.getLastName(), req.getEmail());
        repo.save(user);

        return toResponse(user);
    }

    public UserResponse getById(String id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found: " + id));
        return toResponse(user);
    }

    public UserResponse getByEmail(String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user not found with email: " + email));
        return toResponse(user);
    }

    private UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail());
    }
}
