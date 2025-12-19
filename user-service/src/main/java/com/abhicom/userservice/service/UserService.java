package com.abhicom.userservice.service;

import com.abhicom.userservice.dto.CreateUserRequest;
import com.abhicom.userservice.dto.UserResponse;
import com.abhicom.userservice.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse getByEmail(String email);
    UserResponseDto getUserAddress(Long id);
}
