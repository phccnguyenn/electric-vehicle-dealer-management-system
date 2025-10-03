package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.car.model.enumeration.DriveType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CarDTO {

    private Long id;
    private Integer year;
    private DriveType driveType;
    private Integer seatNumber;
    private LocalDateTime createdAt;

    private Long categoryId;
    private String categoryType;

    private Long colorId;
    private String colorName;
    private String colorHex;
    private Double colorExtraCost;

    private Double rangeMiles;
    private Double accelerationSec;
    private Double topSpeedMph;
    private Double towingLbs;
}
