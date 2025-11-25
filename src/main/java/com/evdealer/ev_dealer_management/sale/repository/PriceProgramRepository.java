package com.evdealer.ev_dealer_management.sale.repository;

import com.evdealer.ev_dealer_management.sale.model.PriceProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceProgramRepository extends JpaRepository<PriceProgram, Long> {

//    List<PriceProgram> findAllByDealerHierarchy(DealerHierarchy dealerHierarchy);

//    List<PriceProgram> findByEffectiveDate(OffsetDateTime effectiveDate);

//    @Modifying
//    @Transactional
//    @Query("UPDATE PriceProgram p SET p.isActive = false")
//    void inactiveAllPrograms();

//    @Modifying
//    @Query("UPDATE PriceProgram p SET p.isActive = false WHERE p.id <> :currentId")
//    void inactiveAllProgramsExcept(@Param("currentId") Long currentId);

    Optional<PriceProgram> findTopByEffectiveDateBeforeOrderByEffectiveDateDesc(OffsetDateTime effectiveDate);

    @Query("SELECT p " +
            "FROM PriceProgram p " +
            "WHERE (p.effectiveDate <= :today OR p.effectiveDate > :today) " +
            "AND p.isActive = true " +
            "ORDER BY p.effectiveDate ASC")
    List<PriceProgram> findCurrentAndUpcomingPricePrograms(@Param("today") OffsetDateTime today);
}
