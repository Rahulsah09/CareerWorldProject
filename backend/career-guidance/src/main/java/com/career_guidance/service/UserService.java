package com.career_guidance.service;

import com.career_guidance.model.User;
import com.career_guidance.repository.UserRepository;
import com.career_guidance.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public User createUser(SignupRequest signupRequest) {
        User user = User.builder()
            .username(signupRequest.getUsername())
            .email(signupRequest.getEmail())
            .password(passwordEncoder.encode(signupRequest.getPassword()))
            .role(com.career_guidance.enums.UserRole.USER)
            .build();
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
    
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
