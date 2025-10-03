package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.startup.EngineConfig;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "engines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engine_id", nullable = false, unique = true)
    private String engineId;

    @Column(name = "engine_details",length = 200, nullable = false)
    private String engineDetails;

    @Column(name = "max_speed", nullable = false)
    private Double maxSpeed   ;

    @OneToMany(mappedBy = "engine",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CarConfig> carConfigs = new ArrayList<>() ;

}
