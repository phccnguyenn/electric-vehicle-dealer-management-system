package com.evdealer.ev_dealer_management.auth.service;

import com.evdealer.ev_dealer_management.auth.model.Token;
import com.evdealer.ev_dealer_management.auth.model.User;
import com.evdealer.ev_dealer_management.auth.model.dto.AuthRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.AuthResponse;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterResponse;
import com.evdealer.ev_dealer_management.auth.model.enumeration.TokenType;
import com.evdealer.ev_dealer_management.auth.repository.TokenRepository;
import com.evdealer.ev_dealer_management.auth.repository.UserRepository;
import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       TokenRepository tokenRepository,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * When login, user will type in username and plain - text password.
     *
     * @param request the authentication request containing the username and plain-text password
     * @return an {@link AuthResponse} object containing the generated JWT access token and refresh token
     *
     * Nhằm hiểu hơn về kĩ thuật này, coi đường link sau:
     * https://www.youtube.com/watch?v=zt8Cocdy15c&t=54s
     */

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.USERNAME_NOT_FOUND, request.getUsername()));


        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }

    public String authentication() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

//    public AuthResponse register(User user) {
//        User savedUser = userRepository.save(user);
//        String jwtToken = jwtService.generateToken(savedUser);
//        String refreshToken = jwtService.generateRefreshToken(savedUser);
//        saveUserToken(savedUser, jwtToken);
//        return AuthResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
//    }

    public RegisterResponse register(RegisterRequest request) {

        if (checkExistUsername(request.username()))
            throw new DuplicatedException(Constants.ErrorCode.USERNAME_ALREADY_EXIST, request.username());

        User user = new User();
        user.setUsername(request.username());
        String hashPassword = passwordEncoder.encode(request.password());
        user.setHashedPassword(hashPassword);
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        boolean isActive = request.isActive() != null;
        user.setActive(isActive);
        user.setRole(request.role());
        User savedUser = userRepository.save(user);

        // Custom
//        String jwtToken = jwtService.generateToken(savedUser);
//        String refreshToken = jwtService.generateRefreshToken(savedUser);
//        saveUserToken(savedUser, jwtToken);
//
//        log.info("ACCESS_TOKEN: " + jwtToken);
//        log.info("REFRESH_TOKEN: " + refreshToken);

        return RegisterResponse.fromModel(savedUser);
    }

    /* @PreAuthorize("hasRole('EVM_ADMIN')")
    public AuthResponse registerUser(AuthRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .hashedPassword(passwordEncoder.encode(request.getPassword()))
                .role(RoleType.DEALER_STAFF) // By default, new registered user will be assigned as STAFF
                .isActive(true) // By default, set user as active
                .build();
        return register(user);
    }
    */
    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;

        for(Token token : validUserTokens) {
            token.setExpired(true);
            token.setRevoked(true);
        }
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            User user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthResponse authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private boolean checkExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }


}

