package com.evdealer.ev_dealer_management.testdrive.model;

import com.evdealer.ev_dealer_management.car.model.Car;
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
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dealer_id", nullable = false)
    private User dealer;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "start_time", nullable = false)
    private OffsetDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private OffsetDateTime endTime;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    // One slot can have many bookings
    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

}
