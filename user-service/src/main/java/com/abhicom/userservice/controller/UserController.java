package com.abhicom.userservice.controller;

import com.abhicom.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;  // constructor injection

    // Constructor Injection again
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /users  -> returns list of usernames (dummy)
    @GetMapping
    public List<String> getUsers() {
        return userService.getAllUsers();
    }

    // POST /users?username=abhishek
    @PostMapping
    public String createUser(@RequestParam String username) {
        userService.createUser(username);
        return "User created: " + username;
    }
}
