package com.clothing.store.service;

import com.clothing.store.dto.auth.AuthResponse;
import com.clothing.store.dto.auth.LoginRequest;
import com.clothing.store.dto.auth.RegisterRequest;
import com.clothing.store.exception.ConflictException;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.mapper.UserMapper;
import com.clothing.store.repository.RoleRepository;
import com.clothing.store.repository.UserRepository;
import com.clothing.store.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email().toLowerCase())) {
            throw new ConflictException("Email already in use");
        }

        var customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new ResourceNotFoundException("Role CUSTOMER not found"));

        var user = new com.clothing.store.entity.User();
        user.setEmail(request.email().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.getRoles().add(customerRole);
        user = userRepository.save(user);

        return buildAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email().toLowerCase(), request.password())
        );

        var user = userRepository.findByEmail(request.email().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return buildAuthResponse(user);
    }

    public com.clothing.store.dto.user.UserMeResponse me(String email) {
        var user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toMeResponse(user);
    }

    private AuthResponse buildAuthResponse(com.clothing.store.entity.User user) {
        var token = jwtService.generateToken(user.getEmail(), Map.of("roles", user.getRoles().stream().map(r -> r.getName()).toList()));
        return new AuthResponse(token, "Bearer", userMapper.toMeResponse(user));
    }
}
