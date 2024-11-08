package com.pablo.nutritional_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pablo.nutritional_tracker.entity.PredefinedFood;
import com.pablo.nutritional_tracker.service.PredefinedFoodService;

import java.util.List;

@RestController
@RequestMapping("/api/predefined-foods")
public class PredefinedFoodController {

	@Autowired
	private PredefinedFoodService predefinedFoodService;

	@PostMapping
	public ResponseEntity<PredefinedFood> createPredefinedFood(@RequestBody PredefinedFood predefinedFood) {
		PredefinedFood createdFood = predefinedFoodService.savePredefinedFood(predefinedFood);
		return ResponseEntity.ok(createdFood);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PredefinedFood> getPredefinedFoodById(@PathVariable Long id) {
		return predefinedFoodService.findPredefinedFoodById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<PredefinedFood>> getAllPredefinedFoods() {
		List<PredefinedFood> foods = predefinedFoodService.findAllPredefinedFoods();
		return ResponseEntity.ok(foods);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePredefinedFood(@PathVariable Long id) {
		predefinedFoodService.deletePredefinedFood(id);
		return ResponseEntity.noContent().build();
	}
}
