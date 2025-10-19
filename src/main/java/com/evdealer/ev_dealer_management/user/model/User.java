package com.evdealer.ev_dealer_management.user.model;

import com.evdealer.ev_dealer_management.auth.model.Token;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.common.model.AbstractAuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Thực thể đại diện cho bảng `users` trong DB, chứa thông tin tài khoản người dùng.
 * trừ mật khẩu ở dạng văn bản (plain-text) để bảo mật.
 * Lớp này sẽ được sử dụng bởi AuthService để tương tác với cơ sở dữ liệu
 * thông qua UserRepository.
 */

@Entity
@Table(schema = "dbo", name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractAuditEntity
        implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy = "parent")
    private List<User> children = new ArrayList<>();

    @OneToMany(mappedBy = "dealer", fetch = FetchType.EAGER)
    private List<Customer> customers = new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Token> tokens;



    /**
     * UserDetails Override
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return hashedPassword;  // TEMP
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
