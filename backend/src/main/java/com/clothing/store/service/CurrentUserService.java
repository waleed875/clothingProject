package com.clothing.store.service;

import com.clothing.store.entity.User;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new ResourceNotFoundException("User not authenticated");
        }

        return userRepository.findByEmail(auth.getName().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
