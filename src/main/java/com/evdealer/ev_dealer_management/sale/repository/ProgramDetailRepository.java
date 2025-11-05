package com.evdealer.ev_dealer_management.sale.repository;

import com.evdealer.ev_dealer_management.sale.model.ProgramDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramDetailRepository extends JpaRepository<ProgramDetail, Long> {
}
