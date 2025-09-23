package com.evdealer.ev_dealer_management.auth.repository;

import com.evdealer.ev_dealer_management.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Theo sát nguyên tắc SOLID - D, interface này chịu trách nhiệm
 * duy nhất về việc truy xuất dữ liệu người dùng từ cơ sở dữ liệu.
 * Nó mở rộng JpaRepository để thừa hưởng các phương thức CRUD cơ bản
 * Hiện tại interface này đang hỗ trợ các phương thức:
 * - tìm kiếm người dùng theo tên đăng nhập
 * - kiểm tra sự tồn tại của tên đăng nhập.
 * (Từ từ sẽ add thêm theo nhu cầu của thầy cô)
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
