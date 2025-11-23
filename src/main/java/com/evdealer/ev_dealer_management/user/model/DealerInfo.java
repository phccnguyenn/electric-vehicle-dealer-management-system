package com.evdealer.ev_dealer_management.user.model;

import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "dbo", name = "dealer_info")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DealerInfo extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dealer_name")
    private String dealerName;

    @ManyToOne
    @JoinColumn(name = "dealer_level")
    private DealerHierarchy dealerHierarchy;

    private String location;

    @Column(name = "contract_file_url")
    private String contractFileUrl;

    @OneToMany
    private List<Customer> customers = new ArrayList<>();

    @OneToMany
    private List<User> users = new ArrayList<>();
}
