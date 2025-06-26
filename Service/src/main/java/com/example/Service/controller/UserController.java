package com.example.Service.controller;

import com.example.Service.dto.CreateUserRequest;
import com.example.Service.dto.LoginUserRequest;
import com.example.Service.dto.UserMapper;
import com.example.Service.dto.UserResponse;
import com.example.Service.model.User;
import com.example.Service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : userService.findAllUsers()){
            userResponses.add(userMapper.toUserResponse(user));
        }

        return ResponseEntity.ok(userResponses);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody CreateUserRequest dto) throws IOException {

        User newUser = userService.saveUser(dto);
        UserResponse savedUser = userMapper.toUserResponse(newUser);

        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(savedUser.id())
                                .toUri())
                .body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserRequest userRequest) {
        boolean authenticated = userService.authenticate(userRequest.email(), userRequest.password());
        if (authenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

}