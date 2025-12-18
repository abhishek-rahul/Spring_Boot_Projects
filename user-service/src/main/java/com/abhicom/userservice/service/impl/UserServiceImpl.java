package com.abhicom.userservice.service.impl;

import com.abhicom.userservice.dto.CreateUserRequest;
import com.abhicom.userservice.dto.UserResponse;
import com.abhicom.userservice.exception.BadRequestException;
import com.abhicom.userservice.exception.NotFoundException;
import com.abhicom.userservice.model.User;
import com.abhicom.userservice.repository.UserRepository;
import com.abhicom.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create a new user
     */
    @Override
    public UserResponse createUser(CreateUserRequest request) {

        // Business validation (before DB constraint)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);
        return toUserResponse(savedUser);
    }

    /**
     * Fetch all users
     */
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toUserResponse)
                .toList();
    }

    /**
     * Fetch user by id
     */
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return toUserResponse(user);
    }

    /**
     * Fetch user by email
     */
    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return toUserResponse(user);
    }

    /**
     * Entity → DTO mapping
     */
    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        return response;
    }
}
