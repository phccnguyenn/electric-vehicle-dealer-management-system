package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long>, JpaSpecificationExecutor<Car> {

    Optional<Car> findByCarModel(String model);
    Car findByCarModelIgnoreCase(String carModel);
    List<Car> findByStatus(CarStatus status);
    List<Car> findByNameContainingIgnoreCase(String name);
}
