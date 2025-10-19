package com.evdealer.ev_dealer_management.user.service;

import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import com.evdealer.ev_dealer_management.common.exception.InvalidAuthenticationPrincipalException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserProfileGetDto;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.evdealer.ev_dealer_management.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class UserService {

    protected final PasswordEncoder passwordEncoder;
    protected final UserRepository userRepository;

    public void changePassword(String oldPassword, String newPassword) {

        User user = getCurrentUser();

        if (!passwordEncoder.matches(oldPassword, user.getHashedPassword()))
             throw new BadCredentialsException("Current password is incorrect");

        if (newPassword.length() < 8)
            throw new IllegalArgumentException("New password must be at least 8 characters long.");

        String hashedNewPassword = passwordEncoder.encode(newPassword);
        user.setHashedPassword(hashedNewPassword);
        userRepository.save(user);
    }

    protected User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = null;
        if (principal instanceof User user)
            currentUser = user;
        else
            throw new InvalidAuthenticationPrincipalException(Constants.ErrorCode.PRINCIPAL_IS_NOT_USER, principal);

        return currentUser;
    }

    protected User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.USER_NOT_FOUND, userId));
    }

    protected void validateUsername(String username) {
        if (checkExistUsername(username)) {
            throw new DuplicatedException(Constants.ErrorCode.USERNAME_ALREADY_EXIST, username);
        }
    }

    protected void validateEmail(String email) {
        if (checkExistEmail(email)) {
            throw new DuplicatedException(Constants.ErrorCode.EMAIL_ALREADY_EXIST, email);
        }
    }

    protected void validatePhoneNumber(String phone) {
        if (checkExistPhone(phone)) {
            throw new DuplicatedException(Constants.ErrorCode.PHONE_ALREADY_EXIST, phone);
        }
    }

    private boolean checkExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean checkExistPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}
