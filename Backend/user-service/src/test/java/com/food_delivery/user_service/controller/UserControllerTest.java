package com.food_delivery.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food_delivery.user_service.entity.Role;
import com.food_delivery.user_service.entity.User;
import com.food_delivery.user_service.jwt.JWTAuthenticationFilter;
import com.food_delivery.user_service.repository.UserRepository;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
class UserControllerIntegrationTest {

    @Container
    static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.4.9");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private QueryGateway queryGateway;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void shouldGetUserByEmail() throws Exception {

        User user = User.builder()
                .email("test@gmail.com")
                .fullName("Vinay")
                .password("password123")
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);

        mockMvc.perform(
                        get("/api/users/viewUser")
                                .param("email", "test@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("test@gmail.com"))
                .andExpect(jsonPath("$.fullName")
                        .value("Vinay"));
    }

    @Test
    void shouldGetUserById() throws Exception {

        User saved = userRepository.save(
                User.builder()
                        .email("user@gmail.com")
                        .fullName("Vinay")
                        .password("password")
                        .role(Role.CUSTOMER)
                        .build()
        );

        mockMvc.perform(
                        get("/api/users/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(saved.getId()))
                .andExpect(jsonPath("$.email")
                        .value("user@gmail.com"));
    }
}