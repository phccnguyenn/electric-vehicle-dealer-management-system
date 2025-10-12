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
@Table(name = "car", schema = "dbo")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Car extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_name")
    private String carName;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "drive_type")
    private DriveType driveType;

    @Column(name = "seat_number")
    private int seatNumber;

    @Column(name ="year")
    private int year;

    @OneToMany(mappedBy = "car", cascade = CascadeType.PERSIST)
    @Column(name = "car_image")
    private List<CarImage> carImages = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    @OneToOne()
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

}
