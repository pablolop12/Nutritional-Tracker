package com.pablo.nutritional_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pablo.nutritional_tracker.entity.Food;
import com.pablo.nutritional_tracker.entity.User;
import com.pablo.nutritional_tracker.repository.FoodRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    /**
     * Guarda un alimento en la base de datos.
     * 
     * @param food El alimento a guardar.
     * @param user El usuario al que pertenece el alimento.
     * @return El alimento guardado.
     */
    public Food saveFood(Food food, User user) {
        // Asegurarse de que el usuario no sea nulo
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo al guardar un alimento.");
        }
        
        // Asignar el usuario al alimento
        food.setUser(user);
        
        return foodRepository.save(food);
    }

    /**
     * Busca un alimento por su ID.
     * 
     * @param id El ID del alimento.
     * @return Un Optional que contiene el alimento si se encuentra, o vacío si no.
     */
    public Optional<Food> findFoodById(Long id) {
        return foodRepository.findById(id);
    }

    /**
     * Encuentra todos los alimentos de un usuario específico.
     * 
     * @param userId El ID del usuario.
     * @return Una lista de alimentos pertenecientes al usuario.
     */
    public List<Food> findFoodsByUserId(Long userId) {
        return foodRepository.findByUserId(userId);
    }

    /**
     * Elimina un alimento por su ID.
     * 
     * @param id El ID del alimento a eliminar.
     */
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }
}
