package com.abhicom.userservice.controller;

import com.abhicom.userservice.dto.CreateUserRequest;
import com.abhicom.userservice.dto.UpdateUserRequest;
import com.abhicom.userservice.dto.UserResponse;
import com.abhicom.userservice.dto.UserResponseDto;
import com.abhicom.userservice.dto.UserSummaryDto;
import com.abhicom.userservice.dto.UserWithOrdersDto;
import com.abhicom.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/users
    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest req) {
        UserResponse created = userService.createUser(req);
        return ResponseEntity.status(201).body(created);
    }

    // GET /api/users
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable String id) {
        long userid = Long.parseLong(id);
        return ResponseEntity.ok(userService.getUserById(userid));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest req) {
        return ResponseEntity.ok(userService.updateUserWithoutSave(id, req));
    }

    @GetMapping("/user_address/{id}")
    public ResponseEntity<UserResponseDto> getUserAddress(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserAddress(id));
    }

    @GetMapping("/{id}/with-orders")
    public ResponseEntity<UserWithOrdersDto> getUserWithOrders(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserWithOrders(id));
    }

    // ✅ GET /api/users?email=...
    // Now email is mandatory and validated here itself
    @GetMapping(params = "email")
    public ResponseEntity<UserResponse> search(
            @RequestParam @NotBlank(message = "email query param is required") @Email(message = "email query param must be valid") String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @GetMapping("/summary-wrong")
    public ResponseEntity<List<UserSummaryDto>> usersSummaryWrong() {
        return ResponseEntity.ok(userService.getUsersSummaryWrongNPlusOne());
    }

    @GetMapping("/summary")
    public ResponseEntity<List<UserSummaryDto>> usersSummary() {
        return ResponseEntity.ok(userService.getUsersSummaryFetchJoin());
    }

    // Content negotiation demo (already from Usecase A)
    @GetMapping(value = "/{id}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> profileJson(@PathVariable String id) {
        long userid = Long.parseLong(id);
        return ResponseEntity.ok(userService.getUserById(userid));
    }

    @GetMapping(value = "/{id}/profile", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> profileText(@PathVariable String id) {
        long userid = Long.parseLong(id);
        UserResponse u = userService.getUserById(userid);
        return ResponseEntity.ok(u.getFirstName() + " " + u.getLastName() + " <" + u.getEmail() + ">");
    }
}
