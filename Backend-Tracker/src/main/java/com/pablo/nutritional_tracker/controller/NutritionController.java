package com.pablo.nutritional_tracker.controller;

import com.pablo.nutritional_tracker.entity.UserDetails;
import com.pablo.nutritional_tracker.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateAndSaveMacros(@RequestBody UserDetails userDetails) {
        if (userDetails.getUser() == null || userDetails.getUser().getId() == null) {
            return ResponseEntity.badRequest().body("UserDetails must have a valid User associated.");
        }

        // Usa el servicio para calcular y guardar
        UserDetails updatedUserDetails = userDetailsService.saveUserDetails(userDetails);
        return ResponseEntity.ok(updatedUserDetails);
    }
}
