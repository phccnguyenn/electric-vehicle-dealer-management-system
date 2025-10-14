package com.evdealer.ev_dealer_management.car.model;

import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "dbo", name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name", unique = true, nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Car> cars = new ArrayList<>();

}
