package com.pablo.nutritional_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pablo.nutritional_tracker.entity.User;
import com.pablo.nutritional_tracker.entity.UserDetails;
import com.pablo.nutritional_tracker.repository.UserRepository;
import com.pablo.nutritional_tracker.repository.UserDetailsRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserDetails> findUserDetailsByUser(User user) {
        return userDetailsRepository.findByUser(user);
    }

    public UserDetails saveUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    public boolean hasCompleteUserDetails(User user) {
        return findUserDetailsByUser(user).isPresent();
    }
    
    public Optional<UserDetails> findUserDetailsByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.flatMap(user -> userDetailsRepository.findByUserId(user.getId()));
    }

}
