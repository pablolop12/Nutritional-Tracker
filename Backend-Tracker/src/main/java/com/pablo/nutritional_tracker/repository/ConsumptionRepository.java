package com.pablo.nutritional_tracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.pablo.nutritional_tracker.entity.Consumption;

import java.util.List;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
    List<Consumption> findByUserId(Long userId);
}