package com.food_delivery.user_service.service.serviceImpl;

import com.food_delivery.user_service.dto.UserDTO;
import com.food_delivery.user_service.entity.User;
import com.food_delivery.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RestClient restClient;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {

        UserDTO inputDto = new UserDTO();
        inputDto.setPassword("12345");

        User userEntity = new User();
        User savedUser = new User();
        savedUser.setId(1L);

        UserDTO responseDto = new UserDTO();

        when(modelMapper.map(inputDto, User.class))
                .thenReturn(userEntity);

        when(passwordEncoder.encode("12345"))
                .thenReturn("encodedPassword");

        when(userRepository.save(userEntity))
                .thenReturn(savedUser);

        when(modelMapper.map(savedUser, UserDTO.class))
                .thenReturn(responseDto);

        UserDTO result = userService.createUser(inputDto);

        assertNotNull(result);
        verify(userRepository).save(userEntity);
    }

    @Test
    void testFindUserByEmail() throws Exception {

        String email = "lokesh@gmail.com";

        User user = new User();
        user.setEmail(email);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(modelMapper.map(user, UserDTO.class))
                .thenReturn(userDTO);

        Optional<UserDTO> result = userService.findUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    void testFindById() {

        Long id = 1L;

        User user = new User();
        user.setId(id);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);

        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        when(modelMapper.map(user, UserDTO.class))
                .thenReturn(userDTO);

        UserDTO result = userService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testDeleteUserById() {

        Long userId = 1L;

        when(userRepository.existsById(userId))
                .thenReturn(true);

        boolean result = userService.deleteUserById(userId);

        assertTrue(result);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void testConvertValue_Long() {

        Object result = userService.convertValue("10", Long.class);

        assertEquals(10L, result);
    }

    @Test
    void testConvertValue_Integer() {

        Object result = userService.convertValue("20", Integer.class);

        assertEquals(20, result);
    }

    @Test
    void testConvertValue_Boolean() {

        Object result = userService.convertValue("true", Boolean.class);

        assertEquals(true, result);
    }
}