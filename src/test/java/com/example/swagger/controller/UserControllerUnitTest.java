package com.example.swagger.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.swagger.dto.UserRequest;
import com.example.swagger.dto.UserResponse;
import com.example.swagger.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testCreateDelegatesToService() {
        var request = new UserRequest("Анна", "anna@example.com");
        when(userService.create(any())).thenReturn("Пользователь создан");

        assertThat(userController.createUser(request)).isEqualTo("Пользователь создан");
        verify(userService).create(request);
    }

    @Test
    void testGetUserDelegatesToService() {
        when(userService.findById("7")).thenReturn(new UserResponse("7", "example@example.com"));

        var response = userController.getUser("7");
        assertThat(response.getId()).isEqualTo("7");
        verify(userService).findById("7");
    }
}
