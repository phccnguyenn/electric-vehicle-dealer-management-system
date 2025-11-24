package com.evdealer.ev_dealer_management.testdrive.repository;

import com.evdealer.ev_dealer_management.testdrive.model.Slot;
import com.evdealer.ev_dealer_management.user.model.DealerInfo;
import com.evdealer.ev_dealer_management.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository
        extends JpaRepository<Slot, Long> {

    List<Slot> findAllByDealerInfo(DealerInfo dealerInfo);

    @Query("SELECT slot FROM Slot slot " +
            "WHERE slot.startTime >= CURRENT_TIMESTAMP")
    List<Slot> findAllOngoingSlots();
}
