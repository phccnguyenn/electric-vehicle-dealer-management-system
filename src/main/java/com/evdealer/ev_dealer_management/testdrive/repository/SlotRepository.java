package com.evdealer.ev_dealer_management.testdrive.repository;

import com.evdealer.ev_dealer_management.testdrive.model.Slot;
import com.evdealer.ev_dealer_management.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository
        extends JpaRepository<Slot, Long> {
    List<Slot> findAllByCreatedBy(String createdBy);
}
