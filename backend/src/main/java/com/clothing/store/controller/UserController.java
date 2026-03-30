package com.clothing.store.controller;

import com.clothing.store.dto.user.UpdateProfileRequest;
import com.clothing.store.dto.user.UserMeResponse;
import com.clothing.store.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserMeResponse getMe() {
        return userService.getMe();
    }

    @PutMapping("/me")
    public UserMeResponse updateMe(@Valid @RequestBody UpdateProfileRequest request) {
        return userService.updateMe(request);
    }
}
