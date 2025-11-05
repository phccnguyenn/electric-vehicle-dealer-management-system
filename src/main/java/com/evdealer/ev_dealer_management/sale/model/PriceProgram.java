package com.evdealer.ev_dealer_management.sale.model;

import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import com.evdealer.ev_dealer_management.user.model.DealerHierarchy;
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

    @ManyToOne
    @JoinColumn(name = "dealer_hierarchy_id")
    private DealerHierarchy dealerHierarchy;

    @Column(name = "start_day", nullable = false)
    private OffsetDateTime startDay;

    @Column(name = "end_day", nullable = false)
    private OffsetDateTime endDay;

    @OneToMany(mappedBy = "priceProgram", cascade = CascadeType.ALL)
    private List<ProgramDetail> programDetails = new ArrayList<>();

}
