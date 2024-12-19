package com.pablo.nutritional_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.pablo.nutritional_tracker.entity.Consumption;
import com.pablo.nutritional_tracker.entity.Food;
import com.pablo.nutritional_tracker.entity.User;
import com.pablo.nutritional_tracker.service.ConsumptionService;
import com.pablo.nutritional_tracker.service.FoodService;
import com.pablo.nutritional_tracker.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consumptions")
public class ConsumptionController {

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createConsumption(@RequestBody Consumption consumptionRequest) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> userOptional = userService.findUserByEmail(userDetails.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            consumptionRequest.setUser(user);

            // Buscar el alimento por su ID
            Optional<Food> foodOptional = foodService.findFoodById(consumptionRequest.getFood().getId());
            if (foodOptional.isPresent()) {
                consumptionRequest.setFood(foodOptional.get());
                consumptionRequest.setConsumedAt(LocalDateTime.now());
                Consumption savedConsumption = consumptionService.saveConsumption(consumptionRequest);
                
                // Crear una respuesta simplificada para evitar bucles infinitos de JSON
                return ResponseEntity.ok(toResponse(savedConsumption));
            } else {
                return ResponseEntity.status(404).body("Alimento no encontrado");
            }
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getConsumptionsByUser(@PathVariable Long userId) {
        List<Consumption> consumptions = consumptionService.findConsumptionsByUserId(userId);
        
        // Convertir cada Consumption a un objeto de respuesta simplificado
        List<Object> response = consumptions.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumption(@PathVariable Long id) {
        consumptionService.deleteConsumption(id);
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para simplificar la respuesta de Consumption
    private Object toResponse(Consumption consumption) {
        return new Object() {
            public final Long id = consumption.getId();
            public final Double quantity = consumption.getQuantity();
            public final LocalDateTime consumedAt = consumption.getConsumedAt();
            public final String foodName = consumption.getFood().getName();
            public final Double calories = consumption.getFood().getCaloriesPer100g();
            public final Double proteins = consumption.getFood().getProteinsPer100g();
            public final Double fats = consumption.getFood().getFatsPer100g();
            public final Double carbs = consumption.getFood().getCarbsPer100g();
            public final Double saturatedFats = consumption.getFood().getSaturatedFatsPer100g();
            public final Double sugars = consumption.getFood().getSugarsPer100g();
            public final Boolean unitBased = consumption.getFood().getUnitBased(); // Incluido unitBased
        };
    }

}
