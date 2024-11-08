package com.pablo.nutritional_tracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_details")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private Double height;

	@Column(nullable = false)
	private Double weight;

	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate; // Nueva fecha de nacimiento

	@Column(name = "body_fat", nullable = false)
	private Double bodyFat;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Goal goal; // Cambiado a Enum

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Sexo sexo; // Nuevo campo de sexo

	@Enumerated(EnumType.STRING)
	@Column(name = "activity_level", nullable = false)
	private ActivityLevel activityLevel; // Nuevo nivel de actividad

	@Column(nullable = false)
	private Integer calories;

	@Column(nullable = false)
	private Integer proteins;

	@Column(nullable = false)
	private Integer fats;

	@Column(nullable = false)
	private Integer carbs;

	@Column(nullable = false)
	private Integer saturatedFats; // Nuevo campo para grasas saturadas

	@Column(nullable = false)
	private Integer sugars; // Nuevo campo para azúcares

	// Getters y Setters
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

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public ActivityLevel getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(ActivityLevel activityLevel) {
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

}
