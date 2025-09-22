package com.evdealer.ev_dealer_management.auth.repository;

import com.evdealer.ev_dealer_management.auth.entity.Role;
import com.evdealer.ev_dealer_management.enums.RoleType;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByRoleName(RoleType roleName);
}
