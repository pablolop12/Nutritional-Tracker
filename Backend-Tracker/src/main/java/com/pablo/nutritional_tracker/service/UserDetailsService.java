package com.pablo.nutritional_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pablo.nutritional_tracker.entity.UserDetails;
import com.pablo.nutritional_tracker.entity.User;
import com.pablo.nutritional_tracker.repository.UserDetailsRepository;
import com.pablo.nutritional_tracker.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NutritionCalculatorService nutritionCalculatorService;

    public UserDetails saveUserDetails(UserDetails userDetails) {
        // Verifica si el objeto User está presente y es válido
        if (userDetails.getUser() == null || userDetails.getUser().getId() == null) {
            throw new IllegalArgumentException("UserDetails must have a valid User associated.");
        }

        // Carga el User desde la base de datos para asegurarse de que es persistente
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Establece el usuario persistente en los detalles del usuario
        userDetails.setUser(user);

        // Calcular las necesidades nutricionales antes de guardar
        nutritionCalculatorService.calculateDailyNeeds(userDetails);

        // Guardar los detalles del usuario
        return userDetailsRepository.save(userDetails);
    }

    public Optional<UserDetails> findUserDetailsById(Long id) {
        return userDetailsRepository.findById(id);
    }

    public void deleteUserDetails(Long id) {
        userDetailsRepository.deleteById(id);
    }
    
    public Optional<UserDetails> findUserDetailsByUserId(Long userId) {
        return userDetailsRepository.findByUserId(userId);
    }
}
