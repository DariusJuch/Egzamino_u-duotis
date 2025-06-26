package com.example.Service.dto;

import com.example.Service.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        Set<RoleResponse> roleResponses = user.getRoles().stream()
                .map(role -> new RoleResponse(role.getId(), role.getName(), role.getAuthority()))
                .collect(Collectors.toSet());


        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roleResponses
        );
    }

    public User toUser (CreateUserRequest dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }
}