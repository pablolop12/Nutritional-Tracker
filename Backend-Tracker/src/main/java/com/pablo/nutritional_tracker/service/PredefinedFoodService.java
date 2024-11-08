package com.pablo.nutritional_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pablo.nutritional_tracker.entity.PredefinedFood;
import com.pablo.nutritional_tracker.repository.PredefinedFoodRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PredefinedFoodService {

    @Autowired
    private PredefinedFoodRepository predefinedFoodRepository;

    public PredefinedFood savePredefinedFood(PredefinedFood predefinedFood) {
        return predefinedFoodRepository.save(predefinedFood);
    }

    public Optional<PredefinedFood> findPredefinedFoodById(Long id) {
        return predefinedFoodRepository.findById(id);
    }

    public List<PredefinedFood> findAllPredefinedFoods() {
        return predefinedFoodRepository.findAll();
    }

    public void deletePredefinedFood(Long id) {
        predefinedFoodRepository.deleteById(id);
    }
}
