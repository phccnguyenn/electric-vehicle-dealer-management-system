package com.evdealer.ev_dealer_management.auth.service;

import com.evdealer.ev_dealer_management.auth.entity.Role;
import com.evdealer.ev_dealer_management.auth.entity.User;
import com.evdealer.ev_dealer_management.auth.repository.UserRepository;
import com.evdealer.ev_dealer_management.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * Create a new user with hashed password and assigned roles.
     * @param username: The UNIQUE username for the new user
     * @param plainPassword: The plain - text password that admin typed in when creating account
     * @param roles: The role that this user will be assigned to
     * @return saved User entity
     */
    @PreAuthorize("hasRole('EVM_ADMIN')")
    public User createUser(String username, String plainPassword, Set<Role> roles) {
        if(userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        // Hash(password + salt (unique string per user)) -> Store in DB
        // When login, retrieve salt from DB, do the same hash function on (password + salt)
        // Compare the result with the hashed password stored in DB
        // If match, login successful, else fail
        String hashedPassword = passwordEncoder.encode(plainPassword);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setHashedPassword(hashedPassword);
        newUser.setRoles(roles);
        newUser.setActive(true); // By default, set user as active

        return userRepository.save(newUser);
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

}
