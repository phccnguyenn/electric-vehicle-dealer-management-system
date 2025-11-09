package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "dbo", name = "car_model")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_model_type", unique = true, nullable = false)
    // @Enumerated(EnumType.STRING)
    private String carModelName;

    @OneToMany(mappedBy = "carModel")
    private List<CarDetail> carDetails = new ArrayList<>();

}
