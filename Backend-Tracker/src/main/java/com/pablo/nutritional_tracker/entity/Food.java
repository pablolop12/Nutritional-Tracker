package com.pablo.nutritional_tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(name = "calories_per_100g", nullable = false)
    private Double caloriesPer100g;

    @Column(name = "proteins_per_100g", nullable = false)
    private Double proteinsPer100g;

    @Column(name = "fats_per_100g", nullable = false)
    private Double fatsPer100g;

    @Column(name = "carbs_per_100g", nullable = false)
    private Double carbsPer100g;

    @Column(name = "saturated_fats_per_100g", nullable = false)
    private Double saturatedFatsPer100g; // Nuevo campo para grasas saturadas

    @Column(name = "sugars_per_100g", nullable = false)
    private Double sugarsPer100g; // Nuevo campo para azúcares

    @Column(name = "unit_based", nullable = false)
    private Boolean unitBased;

    @Column(name = "image_url")
    private String imageUrl;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public void setCaloriesPer100g(Double caloriesPer100g) {
        this.caloriesPer100g = caloriesPer100g;
    }

    public Double getProteinsPer100g() {
        return proteinsPer100g;
    }

    public void setProteinsPer100g(Double proteinsPer100g) {
        this.proteinsPer100g = proteinsPer100g;
    }

    public Double getFatsPer100g() {
        return fatsPer100g;
    }

    public void setFatsPer100g(Double fatsPer100g) {
        this.fatsPer100g = fatsPer100g;
    }

    public Double getCarbsPer100g() {
        return carbsPer100g;
    }

    public void setCarbsPer100g(Double carbsPer100g) {
        this.carbsPer100g = carbsPer100g;
    }

    public Double getSaturatedFatsPer100g() {
        return saturatedFatsPer100g;
    }

    public void setSaturatedFatsPer100g(Double saturatedFatsPer100g) {
        this.saturatedFatsPer100g = saturatedFatsPer100g;
    }

    public Double getSugarsPer100g() {
        return sugarsPer100g;
    }

    public void setSugarsPer100g(Double sugarsPer100g) {
        this.sugarsPer100g = sugarsPer100g;
    }

    public Boolean getUnitBased() {
        return unitBased;
    }

    public void setUnitBased(Boolean unitBased) {
        this.unitBased = unitBased;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
