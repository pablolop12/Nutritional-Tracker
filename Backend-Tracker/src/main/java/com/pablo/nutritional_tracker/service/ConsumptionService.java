package com.pablo.nutritional_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pablo.nutritional_tracker.entity.Consumption;
import com.pablo.nutritional_tracker.repository.ConsumptionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumptionService {

	@Autowired
	private ConsumptionRepository consumptionRepository;

	public Consumption saveConsumption(Consumption consumption) {
		return consumptionRepository.save(consumption);
	}

	public Optional<Consumption> findConsumptionById(Long id) {
		return consumptionRepository.findById(id);
	}

	public List<Consumption> findConsumptionsByUserId(Long userId) {
		return consumptionRepository.findByUserId(userId);
	}

	public void deleteConsumption(Long id) {
		consumptionRepository.deleteById(id);
	}
}
