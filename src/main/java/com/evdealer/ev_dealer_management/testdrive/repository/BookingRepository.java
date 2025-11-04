package com.evdealer.ev_dealer_management.testdrive.repository;

import com.evdealer.ev_dealer_management.testdrive.model.Booking;
import com.evdealer.ev_dealer_management.testdrive.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository
        extends JpaRepository<Booking, Long> {
    List<Booking> findAllBySlot(Slot slot);
    int countBySlot(Slot slot);
}
