package com.pablo.nutritional_tracker.controller;

import com.pablo.nutritional_tracker.service.UserService;
import com.pablo.nutritional_tracker.util.JwtTokenUtil;
import com.pablo.nutritional_tracker.entity.User;
import com.pablo.nutritional_tracker.entity.UserDetails;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        org.springframework.security.core.userdetails.UserDetails userDetails = 
            (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // Método getUsername() del UserDetails de Spring Security
        String jwt = jwtTokenUtil.generateToken(userDetails); // Utiliza la instancia correcta de UserDetails

        // Verificar si el usuario tiene detalles completos
        Optional<UserDetails> userDetailOptional = userService.findUserDetailsByEmail(email);
        boolean hasCompleteDetails = userDetailOptional.isPresent();

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("hasCompleteDetails", hasCompleteDetails);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        Optional<User> userOptional = userService.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Map<String, String> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/details")
    public ResponseEntity<?> saveUserDetails(@RequestBody UserDetails userDetails, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        Optional<User> userOptional = userService.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userDetails.setUser(user);
            UserDetails savedDetails = userService.saveUserDetails(userDetails);
            return ResponseEntity.ok(savedDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
