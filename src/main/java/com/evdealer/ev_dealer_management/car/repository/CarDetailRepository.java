package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.model.CarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarDetailRepository
        extends JpaRepository<CarDetail,Long> {

    // CAST(... AS INT) -- converts that decimal number
    // NEWID()    -- generates a random unique value (a GUID)
    // CHECKSUM() -- Turns that random GUID into a random integer (positive or negative)
    // RAND()     -- normally gives the same random number for every row in a single query
    //            -- This gives a random float between 0 and 1.
    @Query(value = """
                SELECT *
                FROM car_detail
                WHERE (:carModelId IS NULL OR car_model_id = :carModelId)
                    AND (:color IS NULL OR color LIKE :color)
                    AND (:carStatus IS NULL OR car_status = :carStatus)
                ORDER BY NEWID()
            """, nativeQuery = true)
    List<CarDetail> getOneRandomCarDetailByOptionalParameter(
            @Param("carModelId") Long carModelId,
            @Param("color") String color,
            @Param("carStatus") String carStatus
    );

    Page<CarDetail> findAllByCarModel(CarModel carModel, Pageable pageable);

}
