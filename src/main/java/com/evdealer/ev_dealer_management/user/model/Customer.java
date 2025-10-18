package com.evdealer.ev_dealer_management.user.model;

import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "dbo", name = "customer")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer
        extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dealer_id")
    private User dealer;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    private String phone;

    private String address;

}
