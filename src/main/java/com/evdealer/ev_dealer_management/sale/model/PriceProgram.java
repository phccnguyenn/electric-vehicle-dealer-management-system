package com.evdealer.ev_dealer_management.sale.model;

import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "price_program")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceProgram extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "dealer_hierarchy_id")
//    private DealerHierarchy dealerHierarchy;

    @Column(name = "program_name")
    private String programName;

    @Column(name = "effective_date", nullable = false)
    private OffsetDateTime effectiveDate;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "priceProgram", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProgramDetail> programDetails = new ArrayList<>();

}
