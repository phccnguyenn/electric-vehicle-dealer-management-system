package com.evdealer.ev_dealer_management.auth.service;

import com.evdealer.ev_dealer_management.auth.model.dto.AuthRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.AuthResponse;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterResponse;
import com.evdealer.ev_dealer_management.auth.model.Token;
import com.evdealer.ev_dealer_management.common.exception.InvalidAuthenticationPrincipalException;
import com.evdealer.ev_dealer_management.user.model.DealerHierarchy;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.auth.model.enumeration.TokenType;
import com.evdealer.ev_dealer_management.auth.repository.TokenRepository;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserInfoListDto;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserProfileGetDto;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.repository.DealerHierarchyRepository;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final DealerHierarchyRepository dealerHierarchyRepository;

    public UserInfoListDto getAllUser(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserProfileGetDto> userInfoGetDtos = userPage.getContent()
                .stream()
                .map(UserProfileGetDto::fromModel)
                .toList();

        return new UserInfoListDto(
                userInfoGetDtos,
                userPage.getNumber(),
                userPage.getSize(),
                (int) userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
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

    // Get current profile user
    public UserProfileGetDto authentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String) {
            throw new InvalidAuthenticationPrincipalException(
                    Constants.ErrorCode.PRINCIPAL_IS_NOT_USER,
                    auth != null ? auth.getPrincipal() : null
            );
        }

        User user = (User) auth.getPrincipal();
        return UserProfileGetDto.fromModel(user);
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

    public void updateParentId(User user, RegisterRequest request) {

        if (request.role().equals(RoleType.DEALER_STAFF)){
            User parent = userRepository.findByPhone(request.parentPhone())
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_WITH_PHONE_NUMBER_NOT_EXIST, request.parentPhone()));

            user.setParent(parent);
            userRepository.save(user);
            return;
        }

        User admin = userRepository.findByUsername("evd.admin").orElse(null);
        log.info(admin.getFullName());

        if (user.getParent() == null && !user.getRole().equals(RoleType.EVM_ADMIN))
            user.setParent(admin);

        userRepository.save(user);
    }

    public RegisterResponse register(RegisterRequest request) {

        Integer level = null;
        if (request.role() == RoleType.DEALER_MANAGER) {
            level = request.level();
        }

        if (checkExistUsername(request.username()))
            throw new DuplicatedException(Constants.ErrorCode.USERNAME_ALREADY_EXIST, request.username());

        DealerHierarchy dealerHierarchy = null;
        if (request.role() == RoleType.DEALER_MANAGER) {
            dealerHierarchy = dealerHierarchyRepository.findById(Long.valueOf(level.longValue()))
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_HIERARCHY_NOT_FOUND));
        }

        User user = new User();
        user.setUsername(request.username());
        String hashPassword = passwordEncoder.encode(request.password());
        user.setHashedPassword(hashPassword);
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setCity(request.city());
        user.setDealerHierarchy(dealerHierarchy);
        boolean isActive = request.isActive() != null;
        user.setActive(isActive);
        user.setRole(request.role());
        User savedUser = userRepository.save(user);

        if (savedUser.getParent() == null)
            updateParentId(savedUser, request);

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

