package com.abhicom.userservice.controller;

import com.abhicom.userservice.dto.CreateUserRequest;
import com.abhicom.userservice.dto.UserResponse;
import com.abhicom.userservice.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // 1) User registration (JSON -> Java object)
    // POST /api/users
    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest req) {
        UserResponse created = service.createUser(req);
        return ResponseEntity.status(201).body(created);
    }

    // 2) Fetch by id (PathVariable)
    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // 3) Search by email (RequestParam)
    // GET /api/users?email=...
    @GetMapping
    public ResponseEntity<UserResponse> search(
            @RequestParam @NotBlank(message = "email query param is required") @Email(message = "email query param must be valid") String email) {
        return ResponseEntity.ok(service.getByEmail(email));
    }

    // 4) Content negotiation demo (same URL, different produces)
    // GET /api/users/{id}/profile with Accept: application/json
    @GetMapping(value = "/{id}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> profileJson(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // GET /api/users/{id}/profile with Accept: text/plain
    @GetMapping(value = "/{id}/profile", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> profileText(@PathVariable String id) {
        UserResponse u = service.getById(id);
        return ResponseEntity.ok(u.getFirstName() + " " + u.getLastName() + " <" + u.getEmail() + ">");
    }
}
