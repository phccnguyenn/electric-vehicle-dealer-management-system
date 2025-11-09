package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "dbo", name = "car_detail")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarDetail extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @OneToOne
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    @OneToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Column(name = "car_name")
    private String carName;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_status")
    private CarStatus carStatus;

    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "carDetail")
    private List<CarImage> carImages = new ArrayList<>();

}
