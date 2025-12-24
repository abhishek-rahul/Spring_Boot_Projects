package com.abhicom.userservice.service;

import com.abhicom.userservice.dto.CreateUserRequest;
import com.abhicom.userservice.dto.UpdateUserRequest;
import com.abhicom.userservice.dto.UserResponse;
import com.abhicom.userservice.dto.UserResponseDto;
import com.abhicom.userservice.dto.UserSummaryDto;
import com.abhicom.userservice.dto.UserWithOrdersDto;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse getByEmail(String email);
    UserResponseDto getUserAddress(Long id);
    UserResponseDto updateUserWithoutSave(Long id, UpdateUserRequest req);
    UserWithOrdersDto getUserWithOrders(Long id);
    List<UserSummaryDto> getUsersSummaryWrongNPlusOne();
    List<UserSummaryDto> getUsersSummaryFetchJoin();
    void softDeleteUser(Long id);
}
