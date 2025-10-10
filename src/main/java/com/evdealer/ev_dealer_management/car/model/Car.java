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
@Table(name = "car")
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

    @OneToMany(mappedBy = "car")
    @Column(name = "car_image")
    private List<CarImage> carImage = new ArrayList<>();

    @OneToOne(mappedBy = "car", orphanRemoval = true)
    private Interior interior;

    @OneToOne(mappedBy = "car", orphanRemoval = true)
    private Dimension dimension;

    @OneToOne(mappedBy = "car", orphanRemoval = true)
    private Performance performance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

//    @OneToMany(mappedBy = "car", orphanRemoval = true)
//    private List<Long> imagesIds = new ArrayList<>();

}
