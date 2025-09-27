package com.booklovers.myblogapp.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booklovers.myblogapp.model.User;
import com.booklovers.myblogapp.repository.UserRepository;

@Service
public class UserService {
	
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already exists: " + user.getUsername());
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        user.setRole("USER");

        return userRepository.save(user);
    }
    
    public Optional<User> findUser(String username) {
    	return userRepository.findByUsername(username);
    }
	

}
