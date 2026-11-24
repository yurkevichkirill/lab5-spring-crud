package com.example.swagger.controller;

import com.example.swagger.dto.UserRequest;
import com.example.swagger.dto.UserResponse;
import com.example.swagger.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody UserRequest request) {
        return userService.create(request);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        return userService.findById(userId);
    }
}
