package com.pablo.nutritional_tracker.dto;

import java.time.LocalDate;


import com.pablo.nutritional_tracker.entity.UserDetails;

public class UserDetailsDTO {
	
	
	private Long id;

    private Double height;
    private Double weight;
    private LocalDate birthDate;
    private Double bodyFat;
    private String goal;
    private String sexo;
    private String activityLevel;
    private Integer calories;
    private Integer proteins;
    private Integer fats;
    private Integer carbs;
    private Integer saturatedFats;
    private Integer sugars;

    // Constructor
    public UserDetailsDTO(UserDetails userDetails) {
    	
    	this.id= userDetails.getId();    	
        this.height = userDetails.getHeight();
        this.weight = userDetails.getWeight();
        this.birthDate = userDetails.getBirthDate();
        this.bodyFat = userDetails.getBodyFat();
        this.goal = userDetails.getGoal().toString();
        this.sexo = userDetails.getSexo().toString();
        this.activityLevel = userDetails.getActivityLevel().toString();
        this.calories = userDetails.getCalories();
        this.proteins = userDetails.getProteins();
        this.fats = userDetails.getFats();
        this.carbs = userDetails.getCarbs();
        this.saturatedFats = userDetails.getSaturatedFats();
        this.sugars = userDetails.getSugars();
    }
    
    // Getters y Setters

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Double getBodyFat() {
		return bodyFat;
	}

	public void setBodyFat(Double bodyFat) {
		this.bodyFat = bodyFat;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Integer getProteins() {
		return proteins;
	}

	public void setProteins(Integer proteins) {
		this.proteins = proteins;
	}

	public Integer getFats() {
		return fats;
	}

	public void setFats(Integer fats) {
		this.fats = fats;
	}

	public Integer getCarbs() {
		return carbs;
	}

	public void setCarbs(Integer carbs) {
		this.carbs = carbs;
	}

	public Integer getSaturatedFats() {
		return saturatedFats;
	}

	public void setSaturatedFats(Integer saturatedFats) {
		this.saturatedFats = saturatedFats;
	}

	public Integer getSugars() {
		return sugars;
	}

	public void setSugars(Integer sugars) {
		this.sugars = sugars;
	}

   
    
    
}


