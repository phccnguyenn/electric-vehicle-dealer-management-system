package com.evdealer.ev_dealer_management.user.repository;

import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    Page<User> findAllByParentIdAndRole(Long parentId, RoleType role, Pageable pageable);

    Optional<User> findByUsername(String username);
    Optional<User> findByPhone(String phone);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

}
