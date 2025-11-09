package com.evdealer.ev_dealer_management.user.repository;

import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    @Query("SELECT customer FROM Customer customer "
            + "WHERE customer.dealer = :dealer "
            + "ORDER BY customer.fullName ASC")
    Page<Customer> findByDealer(@Param("dealer") User dealer, Pageable pageable);

    Optional<Customer> findByPhone(String phone);
    Optional<Customer> findByPhoneAndDealer(String phone, User dealer);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);


}
