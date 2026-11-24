package com.example.swagger.service;

import com.example.swagger.dto.UserRequest;
import com.example.swagger.dto.UserResponse;
import org.springframework.stereotype.Service;

/**
 * Логика работы с пользователями (по методичке — заглушка без хранилища).
 */
@Service
public class UserService {

    public static final String CREATED_MESSAGE = "Пользователь создан";
    public static final String DEFAULT_EMAIL = "example@example.com";

    public String create(UserRequest request) {
        // Логика создания пользователя
        return CREATED_MESSAGE;
    }

    public UserResponse findById(String userId) {
        // Логика получения пользователя
        return new UserResponse(userId, DEFAULT_EMAIL);
    }
}
