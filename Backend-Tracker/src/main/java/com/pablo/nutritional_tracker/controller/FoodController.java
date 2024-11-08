package com.pablo.nutritional_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.pablo.nutritional_tracker.entity.Food;
import com.pablo.nutritional_tracker.entity.User;
import com.pablo.nutritional_tracker.service.FoodService;
import com.pablo.nutritional_tracker.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;
    private final UserService userService;

    @Autowired
    public FoodController(FoodService foodService, UserService userService) {
        this.foodService = foodService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createFood(@RequestBody Food food) {
        // Obtener la información del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Buscar el usuario en base al email
        Optional<User> userOptional = userService.findUserByEmail(userDetails.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Food savedFood = foodService.saveFood(food, user);

            // Devolver un objeto de respuesta simplificado
            return ResponseEntity.ok(toResponse(savedFood));
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable Long id) {
        return foodService.findFoodById(id)
                .map(food -> ResponseEntity.ok(toResponse(food)))
                .orElse(ResponseEntity.status(404).body("Food no encontrado"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getFoodsByUser() {
        // Obtener la información del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Buscar el usuario en base al email
        Optional<User> userOptional = userService.findUserByEmail(userDetails.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Food> foods = foodService.findFoodsByUserId(user.getId());

            // Convertir cada Food a un objeto de respuesta simplificado
            List<Object> response = foods.stream().map(this::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para simplificar la respuesta de Food
    private Object toResponse(Food food) {
        return new Object() {
            public final Long id = food.getId();
            public final String name = food.getName();
            public final double caloriesPer100g = food.getCaloriesPer100g();
            public final double carbsPer100g = food.getCarbsPer100g();
            public final double fatsPer100g = food.getFatsPer100g();
            public final double proteinsPer100g = food.getProteinsPer100g();
            public final double saturatedFatsPer100g = food.getSaturatedFatsPer100g();
            public final double sugarsPer100g = food.getSugarsPer100g();
            public final String type = food.getType();
            public final String imageUrl = food.getImageUrl();
        };
    }
}
