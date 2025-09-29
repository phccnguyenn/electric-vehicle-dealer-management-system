package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.CarConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarConfigRepository extends JpaRepository<CarConfig,Long> {
      List<CarConfig> findByCarId(Long carId);

}
