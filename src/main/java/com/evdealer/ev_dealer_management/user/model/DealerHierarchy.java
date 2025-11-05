package com.evdealer.ev_dealer_management.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(schema = "dbo", name = "dealer_hierarchy")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DealerHierarchy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dealer_id")
    private User dealer;

    @NotNull
    @Min(value = 1, message = "Level must be at least 1")
    @Max(value = 3, message = "Level must not be greater than 3")
    @Column(name = "level_type")
    private Integer levelType;

}
