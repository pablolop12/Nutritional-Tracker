package com.pablo.nutritional_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pablo.nutritional_tracker.entity.Goal;
import com.pablo.nutritional_tracker.entity.Sexo;
import com.pablo.nutritional_tracker.entity.UserDetails;
import com.pablo.nutritional_tracker.service.UserDetailsService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user-details")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<?> createUserDetails(@RequestBody UserDetails userDetails) {
        UserDetails createdDetails = userDetailsService.saveUserDetails(userDetails);
        return ResponseEntity.ok(toResponse(createdDetails));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserDetailsByUserId(@PathVariable Long userId) {
        return userDetailsService.findUserDetailsByUserId(userId)
                .map(userDetails -> ResponseEntity.ok(toResponse(userDetails)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable Long id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para simplificar la respuesta de UserDetails
    private Object toResponse(UserDetails userDetails) {
        return new Object() {
            public final Long id = userDetails.getId();
            public final Integer calories = userDetails.getCalories();
            public final Integer proteins = userDetails.getProteins();
            public final Integer carbs = userDetails.getCarbs();
            public final Integer fats = userDetails.getFats();
            public final Integer saturatedFats = userDetails.getSaturatedFats();
            public final Integer sugars = userDetails.getSugars();
            public final Double height = userDetails.getHeight();
            public final Double weight = userDetails.getWeight();
            public final Goal goal = userDetails.getGoal();
            public final Sexo sexo = userDetails.getSexo();
            // Agrega otros campos que desees exponer
        };
    }
}
