package com.evdealer.ev_dealer_management.sale.repository;

import com.evdealer.ev_dealer_management.sale.model.PriceProgram;
import com.evdealer.ev_dealer_management.user.model.DealerHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceProgramRepository extends JpaRepository<PriceProgram, Long> {
    List<PriceProgram> findAllByDealerHierarchy(DealerHierarchy dealerHierarchy);
}
