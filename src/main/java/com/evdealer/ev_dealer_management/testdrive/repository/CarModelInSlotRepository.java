package com.evdealer.ev_dealer_management.testdrive.repository;

import com.evdealer.ev_dealer_management.testdrive.model.CarModelInSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelInSlotRepository
        extends JpaRepository<CarModelInSlot, Long> {
}
