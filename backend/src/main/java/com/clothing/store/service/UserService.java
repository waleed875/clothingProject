package com.clothing.store.service;

import com.clothing.store.dto.user.UpdateProfileRequest;
import com.clothing.store.dto.user.UserMeResponse;
import com.clothing.store.mapper.UserMapper;
import com.clothing.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserMeResponse getMe() {
        return userMapper.toMeResponse(currentUserService.getCurrentUser());
    }

    public UserMeResponse updateMe(UpdateProfileRequest request) {
        var user = currentUserService.getCurrentUser();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        return userMapper.toMeResponse(userRepository.save(user));
    }
}
