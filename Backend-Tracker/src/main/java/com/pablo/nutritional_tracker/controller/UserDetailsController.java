package com.pablo.nutritional_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pablo.nutritional_tracker.entity.Goal;
import com.pablo.nutritional_tracker.entity.Sexo;
import com.pablo.nutritional_tracker.entity.UserDetails;
import com.pablo.nutritional_tracker.service.CustomUserDetails;
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

    @PutMapping("/update")
    public ResponseEntity<?> updateUserDetails(
            @RequestBody UserDetails updatedDetails,
            @RequestParam(value = "resetMacros", required = false, defaultValue = "false") boolean resetMacros,
            Authentication authentication) {

        System.out.println("### Entrando al método updateUserDetails");

        // Obtener el usuario autenticado
        String email = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        Optional<UserDetails> existingDetailsOpt = userDetailsService.findUserDetailsByEmail(email);

        if (existingDetailsOpt.isPresent()) {
            UserDetails existingDetails = existingDetailsOpt.get();

            // Actualizar solo los campos no nulos
            if (updatedDetails.getCalories() != null) existingDetails.setCalories(updatedDetails.getCalories());
            if (updatedDetails.getProteins() != null) existingDetails.setProteins(updatedDetails.getProteins());
            if (updatedDetails.getCarbs() != null) existingDetails.setCarbs(updatedDetails.getCarbs());
            if (updatedDetails.getFats() != null) existingDetails.setFats(updatedDetails.getFats());
            if (updatedDetails.getSaturatedFats() != null) existingDetails.setSaturatedFats(updatedDetails.getSaturatedFats());
            if (updatedDetails.getSugars() != null) existingDetails.setSugars(updatedDetails.getSugars());
            if (updatedDetails.getWeight() != null) existingDetails.setWeight(updatedDetails.getWeight());
            if (updatedDetails.getHeight() != null) existingDetails.setHeight(updatedDetails.getHeight());
            if (updatedDetails.getBodyFat() != null) existingDetails.setBodyFat(updatedDetails.getBodyFat());
            if (updatedDetails.getGoal() != null) existingDetails.setGoal(updatedDetails.getGoal());
            if (updatedDetails.getActivityLevel() != null) existingDetails.setActivityLevel(updatedDetails.getActivityLevel());

            // Guardar con opción de recalcular macros
            UserDetails savedDetails = userDetailsService.saveUserDetails(existingDetails, resetMacros);
            return ResponseEntity.ok(savedDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable Long id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.noContent().build();
    }

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
        };
    }
}
