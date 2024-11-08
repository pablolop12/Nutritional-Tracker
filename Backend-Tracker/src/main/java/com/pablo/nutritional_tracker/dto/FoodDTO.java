package com.pablo.nutritional_tracker.dto;

public class FoodDTO {
    private Long id;
    private String name;
    private Double caloriesPer100g;
    private Double proteinsPer100g;
    private Double carbsPer100g;
    private Double fatsPer100g;
    private String type;
    private String imageUrl;
    private Boolean unitBased;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Double getCarbsPer100g() {
		return carbsPer100g;
	}
	public void setCarbsPer100g(Double carbsPer100g) {
		this.carbsPer100g = carbsPer100g;
	}
	public Double getFatsPer100g() {
		return fatsPer100g;
	}
	public void setFatsPer100g(Double fatsPer100g) {
		this.fatsPer100g = fatsPer100g;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Boolean getUnitBased() {
		return unitBased;
	}
	public void setUnitBased(Boolean unitBased) {
		this.unitBased = unitBased;
	}

    // Getters and setters
    
}
