package com.pablo.nutritional_tracker.service;

import org.springframework.stereotype.Service;
import com.pablo.nutritional_tracker.entity.UserDetails;
import com.pablo.nutritional_tracker.entity.ActivityLevel;
import com.pablo.nutritional_tracker.entity.Goal;
import com.pablo.nutritional_tracker.entity.Sexo;

import java.time.LocalDate;
import java.time.Period;

@Service
public class NutritionCalculatorService {

    public void calculateDailyNeeds(UserDetails userDetails) {
        System.out.println("Calculando necesidades nutricionales para: " + userDetails);

        int age = calculateAge(userDetails.getBirthDate());
        double bmr = calculateBMR(userDetails.getWeight(), userDetails.getHeight(), age, userDetails.getSexo());
        double dailyCalories = adjustCaloriesForGoalAndActivity(bmr, userDetails.getGoal(), userDetails.getActivityLevel());

        userDetails.setCalories((int) dailyCalories);
        userDetails.setProteins(calculateProteins(userDetails.getWeight(), userDetails.getGoal()));
        userDetails.setFats(calculateFats(dailyCalories, userDetails.getGoal()));
        userDetails.setCarbs(calculateCarbs(dailyCalories, userDetails.getProteins(), userDetails.getFats()));
        userDetails.setSaturatedFats(calculateSaturatedFats(userDetails.getFats()));
        userDetails.setSugars(calculateSugars(dailyCalories));
        System.out.println("Necesidades calculadas: " + userDetails);

    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private double calculateBMR(double weight, double height, int age, Sexo sexo) {
        if (sexo == Sexo.MASCULINO) {
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }

    private double adjustCaloriesForGoalAndActivity(double bmr, Goal goal, ActivityLevel activityLevel) {
        double adjustedBMR = switch (activityLevel) {
            case UNO_A_DOS_DIAS_SEMANALES -> bmr * 1.375;
            case TRES_A_CINCO_DIAS_SEMANALES -> bmr * 1.55;
            case SIETE_DIAS_SEMANALES -> bmr * 1.725;
        };

        return switch (goal) {
            case PERDIDA_DE_PESO -> adjustedBMR * 0.8;
            case GANANCIA_DE_MASA_MUSCULAR -> adjustedBMR * 1.2;
            case DEFINICION, MANTENIMIENTO -> adjustedBMR;
        };
    }

    private int calculateProteins(double weight, Goal goal) {
        double multiplier = switch (goal) {
            case PERDIDA_DE_PESO -> 2.0;
            case GANANCIA_DE_MASA_MUSCULAR -> 2.2;
            case DEFINICION, MANTENIMIENTO -> 1.8;
        };
        return (int) (weight * multiplier);
    }

    private int calculateFats(double dailyCalories, Goal goal) {
        double fatPercentage = switch (goal) {
            case PERDIDA_DE_PESO -> 0.25;
            case GANANCIA_DE_MASA_MUSCULAR -> 0.3;
            case DEFINICION, MANTENIMIENTO -> 0.3;
        };
        return (int) ((dailyCalories * fatPercentage) / 9);
    }

    private int calculateCarbs(double dailyCalories, int proteins, int fats) {
        int caloriesFromProteins = proteins * 4;
        int caloriesFromFats = fats * 9;
        int remainingCalories = (int) dailyCalories - (caloriesFromProteins + caloriesFromFats);
        return remainingCalories / 4;
    }

    private int calculateSaturatedFats(int fats) {
        return (int) (fats * 0.1);
    }

    private int calculateSugars(double dailyCalories) {
        return (int) ((dailyCalories * 0.1) / 4);
    }
}
