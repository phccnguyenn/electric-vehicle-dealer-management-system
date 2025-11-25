package com.evdealer.ev_dealer_management.sale.model;

import com.evdealer.ev_dealer_management.car.model.CarModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "program_detail", schema = "dbo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "price_program_id")
    private PriceProgram priceProgram;

    @Column(name = "is_special_color", nullable = false)
    private boolean isSpecialColor;

    @Column(name = "listed_price", precision = 15, scale = 2)
    private BigDecimal listedPrice;

}
