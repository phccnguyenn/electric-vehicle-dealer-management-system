package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DimensionRepository extends JpaRepository<Dimension,Integer> {


    Optional<Dimension> findByCarId(Long carId);
}
