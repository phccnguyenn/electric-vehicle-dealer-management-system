package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    Optional<Performance> findPerformanceById(Long id);

}
