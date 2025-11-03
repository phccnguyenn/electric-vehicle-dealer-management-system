package com.evdealer.ev_dealer_management.rating.repository;

import com.evdealer.ev_dealer_management.rating.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository
        extends JpaRepository<Rating, Long> {

    Page<Rating> findAllByOrderByCreatedOnDesc(Pageable pageable);
}
