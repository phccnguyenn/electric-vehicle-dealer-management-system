package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.CarSpecs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarSpecsRepository extends JpaRepository<CarSpecs, Long> {
}
