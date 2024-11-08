package com.pablo.nutritional_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablo.nutritional_tracker.entity.User;
import com.pablo.nutritional_tracker.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
	Optional<UserDetails> findByUser(User user);
    Optional<UserDetails> findByUserId(Long userId);

}