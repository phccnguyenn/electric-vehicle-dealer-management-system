package com.evdealer.ev_dealer_management.user.service;

import com.evdealer.ev_dealer_management.common.exception.InvalidAuthenticationPrincipalException;
import com.evdealer.ev_dealer_management.common.exception.InvalidRoleException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.user.model.dto.account.*;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.evdealer.ev_dealer_management.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService extends UserService {

    public ManufacturerService(PasswordEncoder passwordEncoder,
                               UserRepository userRepository) {
        super(passwordEncoder, userRepository);
    }

    public UserInfoListDto getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);

        return toUserInfoListDto(userPage);
    }

    public UserInfoListDto getAllUsersByRole(int pageNo, int pageSize, RoleType roleType) {

        if (roleType.equals(RoleType.EVM_ADMIN))
            throw new InvalidAuthenticationPrincipalException("Cannot query users with role EVM_ADMIN");

        User currentUser = getCurrentUser();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.findAllByParentIdAndRole(currentUser.getId(), roleType, pageable);

        return toUserInfoListDto(userPage);
    }

    public UserDetailGetDto createUserByAdmin(UserPostDto userPostDto) {

        // UserDetails class
        User currentUser = getCurrentUser();
        User parent = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.USERNAME_NOT_FOUND, currentUser.getId()));

        // Only admin must create new account
        if (parent.getRole() != RoleType.EVM_ADMIN) {
            throw new InvalidRoleException(Constants.ErrorCode.INVALID_ROLE_TYPE, parent.getRole());
        }

        // No permission for other admin to create admin account
        if (userPostDto.role() == RoleType.EVM_ADMIN) {
            throw new InvalidRoleException(Constants.ErrorCode.INVALID_ROLE_TYPE, userPostDto.role());
        }

        // Validate allowed roles
        if (userPostDto.role() != RoleType.EVM_STAFF &&
                userPostDto.role() != RoleType.DEALER_MANAGER &&
                userPostDto.role() != RoleType.DEALER_STAFF) {
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
            validateEmail(userUpdateDto.email());
            user.setEmail(userUpdateDto.email());
        }

        if (userUpdateDto.phone() != null && !userUpdateDto.phone().isBlank()) {
            validatePhoneNumber(userUpdateDto.phone());
            user.setPhone(userUpdateDto.phone());
        }

        userRepository.save(user);
    }

    public void UserBanManagement(Long userId, boolean isBan) {
        User user = getUserById(userId);

        if (isBan) {
            banUser(user);
        } else {
            unbanUser(user);
        }
    }

    private void banUser(User user) {
        user.setActive(false);
        userRepository.save(user);
    }

    private void unbanUser(User user) {
        user.setActive(true);
        userRepository.save(user);
    }

    private UserInfoListDto toUserInfoListDto (Page<User> userPage) {
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

    private User createUserUnderParent(User parent, UserPostDto userPostDto) {

        validateUsername(userPostDto.username());
        validateEmail(userPostDto.email());
        validatePhoneNumber(userPostDto.phone());

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

}
