package com.evdealer.ev_dealer_management.testdrive.model;

import com.evdealer.ev_dealer_management.car.model.CarModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car_model_in_slot", schema = "dbo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarModelInSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @Column(name = "max_trial_car")
    private Integer maxTrialCar;

}
