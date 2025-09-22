package com.evdealer.ev_dealer_management.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Thực thể đại diện cho bảng `roles` trong DB, chứa thông tin về các vai trò người dùng.
 * Hiện tại đang hỗ trợ các vai trò như: EVM_STAFF, EVM_ADMIN, DEALER_STAFF, DEALER_MANAGER.
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id", nullable = false, unique = true)
    private int id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String role;
}
