package com.evdealer.ev_dealer_management.car.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "colors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private  Long colorId;

    @Column(name = "color_name", nullable = false, unique = true)
    private String colorName;

    @Column(name = "color_hex", nullable = false, unique = true)
    private String colorHex;

    @Column(name = "extra_cost")
    private  Float extraCost;

}
