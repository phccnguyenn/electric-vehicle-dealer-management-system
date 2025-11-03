package com.evdealer.ev_dealer_management.rating.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "rating", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_on")
    private OffsetDateTime createdOn;

    @PrePersist
    public void prePersist() {
        if (createdOn == null) {
            createdOn = OffsetDateTime.now(); // set current timestamp with offset
        }
    }

}
