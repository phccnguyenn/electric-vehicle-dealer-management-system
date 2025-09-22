package com.evdealer.ev_dealer_management.enums;

/**
 * Enum đại diện cho các loại vai trò người dùng trong hệ thống.
 * Bao gồm các vai trò như: EVM_STAFF, EVM_ADMIN, DEALER_STAFF, DEALER_MANAGER.
 * Các package đang sử dụng enum này bao gồm:
 * - auth: để xác định vai trò người dùng trong quá trình xác thực và phân quyền
 * - user: để gán và quản lý vai trò cho người dùng
 */
public enum RoleType {
    EVM_STAFF,
    EVM_ADMIN,
    DEALER_STAFF,
    DEALER_MANAGER
}
