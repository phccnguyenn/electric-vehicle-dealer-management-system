package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByStatus(CarStatus status);
    List<Car> findByYear(Integer year);
    Car findByCarModelIgnoreCase(String carModel);
    List<Car> findByCarNameContainingIgnoreCase(String carName);
}
