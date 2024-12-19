package com.pablo.nutritional_tracker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumptions")
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "user_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_consumptions_user")
    )
    private User user;

    @ManyToOne
    @JoinColumn(
        name = "food_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_consumptions_foods", foreignKeyDefinition = "FOREIGN KEY (food_id) REFERENCES foods (id) ON DELETE CASCADE")
    )
    private Food food;

    @Column(nullable = false)
    private Double quantity;

    @Column(name = "consumed_at", nullable = false)
    private LocalDateTime consumedAt;
    
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

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getConsumedAt() {
		return consumedAt;
	}

	public void setConsumedAt(LocalDateTime consumedAt) {
		this.consumedAt = consumedAt;
	}

}