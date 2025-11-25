package com.evdealer.ev_dealer_management.sale.repository;

import com.evdealer.ev_dealer_management.sale.model.ProgramDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramDetailRepository extends JpaRepository<ProgramDetail, Long> {

    List<ProgramDetail> findByPriceProgramId(Long priceProgramId);

    @Query("SELECT d.carModel.id FROM ProgramDetail d WHERE d.priceProgram.id = :programId")
    List<Long> findCarModelIdsByPriceProgram(@Param("programId") Long programId);

    @Query("SELECT pd FROM ProgramDetail pd " +
            "WHERE pd.priceProgram.id = :programId " +
            "AND pd.carModel.id IN :carModelIds")
    List<ProgramDetail> findDetailsByPriceProgramAndCarModelIds(
            @Param("programId") Long programId,
            @Param("carModelIds") List<Long> carModelIds
    );
}
