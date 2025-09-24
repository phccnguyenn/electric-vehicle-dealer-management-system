package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "car_configs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_id", nullable = false, unique = true)
    private Long configId ;
    @Column(name = "color",length = 20, nullable = false)

    private String color ;
    @Column(name = "price",length = 20, nullable = false)
    private BigDecimal price ;
    @Column(name = "image",length = 400, columnDefinition = "TEXT")
    private String image ;
    @Column(name = "version",length = 20, nullable = false)
    private String version ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battery_id", nullable = false)
    private Battery battery ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id", nullable = false)
    private Engine engine ;

    @OneToOne(mappedBy = "config",fetch = FetchType.LAZY)
    private CarSpecs carSpecs;



}
