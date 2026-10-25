package com.food_delivery.user_service.security;


import com.food_delivery.user_service.entity.User;
import com.food_delivery.user_service.jwt.AuthUtil;
import com.food_delivery.user_service.repository.UserRepository;
import com.food_delivery.user_service.request.SignUpRequestDto;
import com.food_delivery.user_service.response.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
//    private final CartRepository cartRepository;
    private final CustomUserDetailService customUserDetailService;



    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto) {

        // 1. Check if user already exists
        User existingUser = userRepository
                .findByEmail(signUpRequestDto.getEmail())
                .orElse(null);

        if (existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        }

        // 2. Create new user from DTO
        User newUser = User.builder()
                .email(signUpRequestDto.getEmail())
                .fullName(signUpRequestDto.getFullName())
                .role(signUpRequestDto.getRole())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .build();

        User savedUser = userRepository.save(newUser);

        // 3. Create cart for new user
//        Cart cart = Cart.builder()
//                .customer(savedUser)
//                .build();
//
//        cartRepository.save(cart);

        // 4 Return response
        return new SignUpResponseDto(savedUser.getId(), savedUser.getEmail());
    }
}
