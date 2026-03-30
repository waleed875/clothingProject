package com.clothing.store.dto.auth;

import com.clothing.store.dto.user.UserMeResponse;

public record AuthResponse(String accessToken, String tokenType, UserMeResponse user) {
}
