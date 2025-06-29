package com.example.Service.service;

import com.example.Service.dto.CreateUserRequest;

import com.example.Service.dto.UserMapper;
import com.example.Service.exception.EmailAlreadyExistsException;
import com.example.Service.exception.UsernameAlreadyExistsException;
import com.example.Service.model.Role;

import com.example.Service.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Service.repository.RoleRepository;
import com.example.Service.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User save(User user) {
        return userRepository.save(user);
    }

    public User saveUser(CreateUserRequest dto) throws IOException {
        if (userRepository.existsByEmail(dto.email())){
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        if (userRepository.existsByUsername(dto.username())){
            throw new UsernameAlreadyExistsException("Username already exists.");
        }

        Role roleUser = roleRepository.findByName("USER").orElseThrow();

        User newUser = userMapper.toUser(dto);

        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.setRoles(Set.of(roleUser));

        return userRepository.save(newUser);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean authenticate(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}