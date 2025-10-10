package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.Motor;
import com.evdealer.ev_dealer_management.car.model.enumeration.MotorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotorRepository extends JpaRepository<Motor,Long> {

    Optional<Motor> findByMotorType(MotorType motorType );

    @Query("SELECT motor FROM Motor motor WHERE motor.serialNumber = :serialNumber")
    Optional<Motor> findByExistedSerialNumber(@Param("serialNumber") String serialNumber);
    Optional<Motor> findBySerialNumber(String serialNumber);
}
