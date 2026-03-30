package com.clothing.store.mapper;

import com.clothing.store.dto.user.UserMeResponse;
import com.clothing.store.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserMeResponse toMeResponse(User user) {
        return new UserMeResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles().stream().map(role -> role.getName()).collect(java.util.stream.Collectors.toSet())
        );
    }
}
