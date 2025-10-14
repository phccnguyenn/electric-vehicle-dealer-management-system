package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarImageRepository
        extends JpaRepository<CarImage, Long> {

    List<CarImage> findByCarId(@Param("carId") Long carId);

}
