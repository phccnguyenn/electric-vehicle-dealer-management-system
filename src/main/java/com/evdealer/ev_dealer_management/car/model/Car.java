package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "dbo", name = "car")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Car extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToOne
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    @OneToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Column(name = "car_name")
    private String carName;

    @Enumerated(EnumType.STRING)
    @Column(name = "drive_type")
    private DriveType driveType;

    private int year;

    private BigDecimal price;

    @OneToMany(mappedBy = "car")
    private List<CarImage> carImages = new ArrayList<>();

}
