package com.pablo.nutritional_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablo.nutritional_tracker.entity.Food;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByUserId(Long userId);
}