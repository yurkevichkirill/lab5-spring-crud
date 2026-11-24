package com.example.swagger.controller;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testApiDocsJson() throws Exception {
        var body = mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThatJson(body).node("openapi").isString().startsWith("3.");
        assertThatJson(body).node("paths").isObject()
                .containsKeys("/users/create", "/users/{userId}");
        assertThatJson(body).node("components.schemas").isObject()
                .containsKeys("UserRequest", "UserResponse");
        assertThatJson(body).node("components.schemas.UserRequest.properties").isObject()
                .containsKeys("name", "email");
        assertThatJson(body).node("components.schemas.UserResponse.properties").isObject()
                .containsKeys("id", "email");
    }

    @Test
    void testApiDocsYaml() throws Exception {
        var body = mockMvc.perform(get("/v3/api-docs.yaml"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(body).contains("openapi:", "/users/create:", "/users/{userId}:");
    }

    @Test
    void testSwaggerUiRedirect() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/swagger-ui/index.html"));
    }

    @Test
    void testSwaggerUiIndex() throws Exception {
        var body = mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(body).contains("Swagger UI");
    }
}
