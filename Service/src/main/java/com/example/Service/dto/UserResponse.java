package com.example.Service.dto;

import java.util.Set;

public record UserResponse(
        long id,
        String username,
        String email,
        Set<RoleResponse> roles
) {
}