package com.evdealer.ev_dealer_management.auth.service;

import com.evdealer.ev_dealer_management.auth.entity.Role;
import com.evdealer.ev_dealer_management.auth.entity.User;
import com.evdealer.ev_dealer_management.auth.repository.UserRepository;
import com.evdealer.ev_dealer_management.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    // Best practise: use constructor injection
    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * When login, user will type in username and plain - text password.
     *
     * @param username: username typed in by user
     * @param password: plain - text password typed in by user
     * @return User object if login successful, else throw exception
     *
     * Nhằm hiểu hơn về kĩ thuật này, coi đường link sau:
     * https://www.youtube.com/watch?v=zt8Cocdy15c&t=54s
     */

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if(!user.isActive()) {
            throw new RuntimeException("User account is inactive!");
        }

        if(!passwordEncoder.matches(password, user.getHashedPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        //Login successful
        return user;
    }

    /**
     * Checks if the user has at least one of the required roles.
     *
     * @param user User entity
     * @param requiredRoles Roles allowed for this action
     * @throws RuntimeException if user does not have permission
     */
    public void checkRole(User user, Set<RoleType> requiredRoles) {
        boolean hasRole = user.getRoles().stream()
                .map(Role::getRoleName)
                .anyMatch(requiredRoles::contains);

        if (!hasRole) {
            throw new RuntimeException("User does not have permission to perform this action");
        }
    }


}
