package com.evdealer.ev_dealer_management.user.service;

import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import com.evdealer.ev_dealer_management.common.exception.InvalidAuthenticationPrincipalException;
import com.evdealer.ev_dealer_management.common.exception.InvalidRoleException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.user.model.dto.*;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.evdealer.ev_dealer_management.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import com.evdealer.ev_dealer_management.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserInfoListDto getAllUser(int pageNo, int pageSize, RoleType roleType) {

        if (roleType.equals(RoleType.EVM_ADMIN))
            throw new InvalidAuthenticationPrincipalException("Cannot query users with role EVM_ADMIN");

        User currentUser = getCurrentUser();
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<User> userPage =
                userRepository.findAllByParentIdAndRole(currentUser.getId(), roleType, pageable);

        List<UserProfileGetDto> userInfoGetDtos = userPage.getContent()
                .stream()
                .map(UserProfileGetDto::fromModel)
                .toList();

        return new UserInfoListDto (
                userInfoGetDtos,
                userPage.getNumber(),
                userPage.getSize(),
                (int) userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
    }

    public UserDetailGetDto userCreationByAdmin(UserPostDto userPostDto) {

        // UserDetails class
        User currentUser = getCurrentUser();
        User parent = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.USERNAME_NOT_FOUND, currentUser.getId()));

        if (parent.getRole() == RoleType.EVM_ADMIN) {
            if (!(userPostDto.role().equals(RoleType.EVM_STAFF)
                    || userPostDto.role().equals(RoleType.DEALER_MANAGER)))
                throw new InvalidRoleException(Constants.ErrorCode.INVALID_ROLE_TYPE, userPostDto.role());
        }

        User child = createUserUnderParent(parent, userPostDto);

        parent.getChildren().add(child);
        return UserDetailGetDto.fromModel(userRepository.save(parent));
    }

    public void updatePartialUser(Long userId, UserUpdateDto userUpdateDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.USER_NOT_FOUND, userId));

        if (userUpdateDto.fullName() != null && !userUpdateDto.fullName().isBlank()) {
            user.setFullName(userUpdateDto.fullName());
        }

        if (userUpdateDto.email() != null && !userUpdateDto.email().isBlank()) {

            if (checkExistEmail(userUpdateDto.email()))
                throw new DuplicatedException(Constants.ErrorCode.EMAIL_ALREADY_EXIST, userUpdateDto.email());

            user.setEmail(userUpdateDto.email());
        }

        if (userUpdateDto.phone() != null && !userUpdateDto.phone().isBlank()) {
            if (checkExistPhone(userUpdateDto.phone()))
                throw new DuplicatedException(Constants.ErrorCode.PHONE_ALREADY_EXIST, userUpdateDto.phone());

            user.setPhone(userUpdateDto.phone());
        }

        userRepository.save(user);
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = null;
        if (principal instanceof User user)
            currentUser = user;
        else
            throw new InvalidAuthenticationPrincipalException(Constants.ErrorCode.PRINCIPAL_IS_NOT_USER, principal);

        return currentUser;
    }

    private User createUserUnderParent(User parent, UserPostDto userPostDto) {

        if (checkExistUsername(userPostDto.username()))
            throw new DuplicatedException(Constants.ErrorCode.USERNAME_ALREADY_EXIST, userPostDto.username());

        if (checkExistEmail(userPostDto.email()))
            throw new DuplicatedException(Constants.ErrorCode.EMAIL_ALREADY_EXIST, userPostDto.email());

        if (checkExistPhone(userPostDto.phone()))
            throw new DuplicatedException(Constants.ErrorCode.PHONE_ALREADY_EXIST, userPostDto.phone());

        String hashedPassword = passwordEncoder.encode(userPostDto.password());
        User newUser = User.builder()
                .parent(parent)
                .username(userPostDto.username())
                .hashedPassword(hashedPassword)
                .fullName(userPostDto.fullName())
                .email(userPostDto.email())
                .phone(userPostDto.phone())
                .isActive(userPostDto.isActive())
                .role(userPostDto.role())
                .build();
        return userRepository.save(newUser);
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
