package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.Battery;
import com.evdealer.ev_dealer_management.car.model.enumeration.Chemistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
    Optional<Battery> findByChemistry(Chemistry chemistry);
}
