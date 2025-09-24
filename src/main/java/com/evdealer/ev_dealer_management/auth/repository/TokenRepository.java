package com.evdealer.ev_dealer_management.auth.repository;

import com.evdealer.ev_dealer_management.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    @Query(value = """
            SELECT t FROM Token t JOIN User u\s
            ON t.user.id = u.id
            WHERE u.id = :userId
            AND (t.expired = false OR t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(Long userId);
}
