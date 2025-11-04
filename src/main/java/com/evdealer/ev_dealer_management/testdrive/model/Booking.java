package com.evdealer.ev_dealer_management.testdrive.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking", schema = "dbo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

    @Column(name = "customer_phone", nullable = false, length = 20)
    private String customerPhone;

}
