package com.evdealer.ev_dealer_management.car.model;


import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "dbo", name = "color")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Color extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "color_name", nullable = false, unique = true)
    private String colorName;

    @Column(name = "color_hex_code", nullable = false, unique = true)
    private String colorHexCode;

    @Column(name = "extra_cost")
    private BigDecimal extraCost;

    @OneToMany(mappedBy = "color")
    public List<Car> cars = new ArrayList<>();

}
