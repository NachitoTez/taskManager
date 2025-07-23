package com.lemon.taskmanager.tasks.controller;

import com.lemon.taskmanager.auth.controller.dto.AuthResponse;
import com.lemon.taskmanager.auth.controller.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TasksControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void shouldReturn401WhenAccessingTasksWithoutToken() {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl("/tasks"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldAllowAccessToTasksWithValidToken() {
        // Paso 1: Registro de usuario
        RegisterRequest request = new RegisterRequest("obi.wan", "theforce", "member");
        ResponseEntity<AuthResponse> registerResponse = restTemplate.postForEntity(
                getBaseUrl("/auth/register"),
                request,
                AuthResponse.class
        );
        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = Objects.requireNonNull(registerResponse.getBody()).token();

        // Paso 2: GET /tasks con Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> taskResponse = restTemplate.exchange(
                getBaseUrl("/tasks"),
                HttpMethod.GET,
                entity,
                String.class
        );

        assertThat(taskResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
