package com.food_delivery.user_service.service.serviceImpl;

import com.food_delivery.user_service.advices.ApiResponse;
import com.food_delivery.user_service.dto.UserDTO;
import com.food_delivery.user_service.entity.User;
import com.food_delivery.user_service.exception.ResourceNotFoundException;
import com.food_delivery.user_service.repository.UserRepository;
import com.food_delivery.user_service.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestClient restClient;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    @Transactional          // when we use @Transactional then we are in Transactional Context
    public void findByUserId(Integer id) {
        User user1 = userRepository.findById(id);           // and user1 & user2 both are in persistence state
        User user2 = userRepository.findById(id);           // Here all the operation down are save in the persistence context, means the findById output will store in the persistence state
        System.out.println(user1 + " " + user2);
        System.out.println(user1 == user2);
    }

    @Override
    public User findUserByJwtToken(String ignored) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {

            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found with email: " + email));
    }

    @Override
    public Optional<UserDTO> findUserByEmail(String email) throws Exception {
//        Optional<User> findUser = userRepository.findByEmail(email);
//        return findUser.map(user -> modelMapper.map(user, UserDTO.class));
        return userRepository.findByEmail(email).map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public List<UserDTO> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
        try {
            ApiResponse<List<UserDTO>> users = restClient.get()
                    .uri("api/users/view-all-users")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return users.getData();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDTO createUser(UserDTO inputUser) {
        User toSaveEntity = modelMapper.map(inputUser, User.class);
        toSaveEntity.setPassword(passwordEncoder.encode(inputUser.getPassword()));
        User savedUserEntity = userRepository.save(toSaveEntity);
        return modelMapper.map(savedUserEntity, UserDTO.class);
    }

    @Override
    public UserDTO updateUserById(UserDTO userDTO, Long userId) {
        User user = modelMapper.map(userDTO, User.class);
        user.setId(userId);
        User saveUserEntity = userRepository.save(user);
        return modelMapper.map(saveUserEntity, UserDTO.class);
    }

    public void isExistsByUserId(Long userId) {
        boolean exists =  userRepository.existsById(userId);
        if(!exists) throw new ResourceNotFoundException("User not found with id : "+userId);
    }

    @Override
    public boolean deleteUserById(Long userId) {
        isExistsByUserId(userId);
        userRepository.deleteById(userId);
        return true;
    }

    @Override
    public UserDTO updatePartialUserById(Map<String, Object> update, Long userId) {
        isExistsByUserId(userId);
        User user = userRepository.findById(userId).get();
        update.forEach((field, value) -> {
            Field fieldToBeUpdated = org.springframework.util.ReflectionUtils.findField(User.class, field);
            if (fieldToBeUpdated != null) {
                fieldToBeUpdated.setAccessible(true);           // we are giving access to the private variable of the class
                Object convertedValue = convertValue(value, fieldToBeUpdated.getType());
                ReflectionUtils.setField(fieldToBeUpdated, user, convertedValue);
            }
        });
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not Found"));
        return modelMapper.map(user, UserDTO.class);
    }

    public Object convertValue(Object value, Class<?> type) {
        if (type == Integer.class || type == int.class) {
            return Integer.parseInt(value.toString());
        } else if (type == Long.class || type == long.class) {
            return Long.parseLong(value.toString());
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(value.toString());
        }
        return value;
    }
}
