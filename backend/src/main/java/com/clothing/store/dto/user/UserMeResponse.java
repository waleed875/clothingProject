package com.clothing.store.dto.user;

import java.util.Set;

public record UserMeResponse(Long id, String email, String firstName, String lastName, Set<String> roles) {
}
