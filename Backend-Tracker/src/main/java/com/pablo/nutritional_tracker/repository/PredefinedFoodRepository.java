package com.pablo.nutritional_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablo.nutritional_tracker.entity.PredefinedFood;

public interface PredefinedFoodRepository extends JpaRepository<PredefinedFood, Long> {
}