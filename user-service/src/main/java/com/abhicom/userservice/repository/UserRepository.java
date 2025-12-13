package com.abhicom.userservice.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    // Temporary in-memory storage (just for skeleton)
    private final List<String> users = new ArrayList<>();

    public List<String> findAll() {
        return users;
    }

    public void save(String username) {
        users.add(username);
    }

    // Later: replace this with real JPA / DB
}
