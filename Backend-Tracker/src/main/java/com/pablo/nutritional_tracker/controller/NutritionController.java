package com.pablo.nutritional_tracker.controller;

import com.pablo.nutritional_tracker.entity.User;
import com.pablo.nutritional_tracker.entity.UserDetails;
import com.pablo.nutritional_tracker.service.UserDetailsService;
import com.pablo.nutritional_tracker.service.UserService;
import com.pablo.nutritional_tracker.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateAndSaveMacros(
            @RequestHeader("Authorization") String token,
            @RequestBody UserDetails userDetails) {

        try {
            // Extraer el email del token
            String email = jwtTokenUtil.extractUsername(token.replace("Bearer ", ""));

            // Buscar el usuario por email
            Optional<User> userOptional = userService.findUserByEmail(email);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                userDetails.setUser(user);

                UserDetails updatedUserDetails = userDetailsService.saveUserDetails(userDetails);
                return ResponseEntity.ok(updatedUserDetails);
            } else {
                return ResponseEntity.status(404).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Error al procesar la solicitud: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error al procesar los datos: " + e.getMessage());
        }
    }
}
