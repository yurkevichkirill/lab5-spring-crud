package com.example.swagger.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.swagger.dto.UserRequest;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private final UserService userService = new UserService();

    @Test
    void testCreateReturnsMessage() {
        var result = userService.create(new UserRequest("Иван", "ivan@example.com"));
        assertThat(result).isEqualTo("Пользователь создан");
    }

    @Test
    void testFindByIdReturnsResponseWithSameId() {
        var response = userService.findById("42");
        assertThat(response.getId()).isEqualTo("42");
        assertThat(response.getEmail()).isEqualTo("example@example.com");
    }
}
