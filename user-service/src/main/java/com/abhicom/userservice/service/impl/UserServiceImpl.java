package com.abhicom.userservice.service.impl;

import com.abhicom.userservice.dto.AddressDto;
import com.abhicom.userservice.dto.CreateUserRequest;
import com.abhicom.userservice.dto.UpdateUserRequest;
import com.abhicom.userservice.dto.UserResponse;
import com.abhicom.userservice.dto.UserResponseDto;
import com.abhicom.userservice.exception.BadRequestException;
import com.abhicom.userservice.exception.NotFoundException;
import com.abhicom.userservice.model.User;
import com.abhicom.userservice.repository.UserRepository;
import com.abhicom.userservice.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public UserResponseDto getUserAddress(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return toUserAddressResponse(user);
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


    /**
     * Entity → DTO mapping
     */
    private UserResponseDto toUserAddressResponse(User user) {
        UserResponseDto response = new UserResponseDto();

        List<AddressDto> addressDtos = user.getAddresses().stream().map(address -> {
            AddressDto addressDto = new AddressDto();
            addressDto.setCity(address.getCity());
            addressDto.setState(address.getState());
            addressDto.setCountry(address.getState());
            addressDto.setPincode(address.getPincode());;
            return addressDto;
        }).toList();

        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setAddresses(addressDtos);
        return response;
    }

    @Transactional
    public UserResponseDto updateUserWithoutSave(Long id, UpdateUserRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2) Modify fields (NO save call)
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());

        // ✅ force flush NOW (SQL goes to DB immediately, but transaction not committed yet)
        entityManager.flush();

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        // if you have addresses/orders mapping, keep consistent or omit here
        return dto;
    }
    
}
