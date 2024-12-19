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
        return saveUserDetails(userDetails, false); // Por defecto, no recalcula los macros
    }

    public UserDetails saveUserDetails(UserDetails userDetails, boolean resetMacros) {
        System.out.println("Detalles del usuario recibidos: " + userDetails);

        if (userDetails.getUser() == null || userDetails.getUser().getId() == null) {
            throw new IllegalArgumentException("El usuario asociado no es válido.");
        }

        if (resetMacros) {
            System.out.println("### Recalculando macros automáticamente");
            nutritionCalculatorService.calculateDailyNeeds(userDetails);
        }

        UserDetails savedDetails = userDetailsRepository.save(userDetails);
        System.out.println("Detalles del usuario guardados: " + savedDetails);

        return savedDetails;
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
    
 // Método para buscar los detalles de usuario por email
    public Optional<UserDetails> findUserDetailsByEmail(String email) {
        return userDetailsRepository.findByUser_Email(email);
    }
    
    
    
    
}
