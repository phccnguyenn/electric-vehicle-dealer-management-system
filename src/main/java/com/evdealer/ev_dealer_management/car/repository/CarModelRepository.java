package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.CarModel;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelRepository
        extends JpaRepository<CarModel, Long> {
    List<CarModel> findDistinctByCarDetails_CarStatus(CarStatus carStatus);
}
