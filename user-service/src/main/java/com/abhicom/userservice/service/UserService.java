package com.abhicom.userservice.service;

import com.abhicom.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;  // constructor injection

    // Constructor Injection (recommended way)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(String username) {
        // Here we could add validation, business rules, etc.
        userRepository.save(username);
    }
}
