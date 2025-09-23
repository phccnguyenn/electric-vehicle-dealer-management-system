package com.evdealer.ev_dealer_management.auth.entity;

import com.evdealer.ev_dealer_management.auth.entity.enumeration.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.HashSet;
/**
 * Thực thể đại diện cho bảng `roles` trong DB, chứa thông tin về các vai trò người dùng.
 * Hiện tại đang hỗ trợ các vai trò như: EVM_STAFF, EVM_ADMIN, DEALER_STAFF, DEALER_MANAGER.
 */
@ToString(exclude="users")
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id", nullable = false, unique = true)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true)
    private RoleType roleName;

    // 1 arg-Constructor
    public Role(RoleType roleName) {
        this.roleName = roleName;
    }

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
