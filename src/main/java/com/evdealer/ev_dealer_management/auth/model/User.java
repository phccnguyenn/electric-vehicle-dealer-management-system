package com.evdealer.ev_dealer_management.auth.model;

import com.evdealer.ev_dealer_management.auth.model.enumeration.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;
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
@Data
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    private RoleType role;

    // Một người có thể có nhiều tokens
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;

    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
