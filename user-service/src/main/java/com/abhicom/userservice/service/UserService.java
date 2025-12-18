package com.abhicom.userservice.service;

import com.abhicom.userservice.dto.CreateUserRequest;
import com.abhicom.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse getByEmail(String email);
}
