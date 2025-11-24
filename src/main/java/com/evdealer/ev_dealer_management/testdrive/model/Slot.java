package com.evdealer.ev_dealer_management.testdrive.model;

import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import com.evdealer.ev_dealer_management.user.model.DealerInfo;
import com.evdealer.ev_dealer_management.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "slot", schema = "dbo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Slot extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dealer_info_id", nullable = false)
    private DealerInfo dealerInfo;

    @ManyToOne
    @JoinColumn(name = "dealer_staff_id", nullable = false)
    private User dealerStaff;      // staff who is in charge of managing this slot

    @Column(name = "num_customers", nullable = false)
    private Integer numCustomers;

    @Column(name = "start_time", nullable = false)
    private OffsetDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private OffsetDateTime endTime;

    // One slot can have many bookings
    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "slot")
    @Builder.Default
    private List<CarModelInSlot> carModelInSlots = new ArrayList<>();

}
