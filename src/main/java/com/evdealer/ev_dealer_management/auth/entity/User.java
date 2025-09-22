package com.evdealer.ev_dealer_management.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;

/**
 * Thực thể đại diện cho bảng `users` trong DB, chứa thông tin tài khoản người dùng.
 * trừ mật khẩu ở dạng văn bản (plain-text) để bảo mật.
 * Lớp này sẽ được sử dụng bởi AuthService để tương tác với cơ sở dữ liệu
 * thông qua UserRepository.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="roles")
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    @NotNull
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    // Chỗ này mình đổi password -> hashed_password nha anh Phúc
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
