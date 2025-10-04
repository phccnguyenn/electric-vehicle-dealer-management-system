package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface    ColorRepository extends JpaRepository<Color, Long> {

    Optional<Color> findByColorName(String colorName);

}
