package com.planner.userservice.controller;

import com.planner.userservice.entity.User;
import com.planner.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        if (allUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getEmail());
        User userExists = userService.getUserByEmail(user.getEmail());
        if(userExists != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
}
