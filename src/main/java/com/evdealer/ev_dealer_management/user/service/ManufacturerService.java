package com.evdealer.ev_dealer_management.user.service;

import com.evdealer.ev_dealer_management.common.exception.InvalidAuthenticationPrincipalException;
import com.evdealer.ev_dealer_management.common.exception.NoPermissionException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.user.model.DealerHierarchy;
import com.evdealer.ev_dealer_management.user.model.DealerInfo;
import com.evdealer.ev_dealer_management.user.model.dto.dealer.DealerInfoGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.dealer.DealerInfoPostDto;
import com.evdealer.ev_dealer_management.user.model.dto.dealer.DealerUserPostDto;
import com.evdealer.ev_dealer_management.user.model.dto.account.*;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.repository.DealerHierarchyRepository;
import com.evdealer.ev_dealer_management.user.repository.DealerInfoRepository;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.evdealer.ev_dealer_management.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ManufacturerService extends UserService {

    private final DealerInfoRepository dealerInfoRepository;
    private final RegistryFileGenerator registryFileGenerator;

    public ManufacturerService(PasswordEncoder passwordEncoder,
                               UserRepository userRepository,
                               DealerHierarchyRepository dealerHierarchyRepository,
                               DealerInfoRepository dealerInfoRepository,
                               RegistryFileGenerator registryFileGenerator) {
        super(passwordEncoder, userRepository, dealerHierarchyRepository);
        this.dealerInfoRepository = dealerInfoRepository;
        this.registryFileGenerator = registryFileGenerator;
    }

    public UserInfoListDto getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);

        return toUserInfoListDto(userPage);
    }

    public UserInfoListDto getAllUsersByRole(int pageNo, int pageSize, RoleType roleType) {

        if (roleType.equals(RoleType.EVM_ADMIN))
            throw new InvalidAuthenticationPrincipalException("Cannot query users with role EVM_ADMIN");

        // User currentUser = getCurrentUser();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.findAllByRole(roleType, pageable);

        return toUserInfoListDto(userPage);
    }

//    public UserDetailGetDto createUserByAdmin(UserPostDto userPostDto) {
//
//        // UserDetails class
//        User currentUser = getCurrentUser();
//        User parent = userRepository.findById(currentUser.getId())
//                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.USERNAME_NOT_FOUND, currentUser.getId()));
//
//        // Only admin must create new account
//        if (parent.getRole() != RoleType.EVM_ADMIN && parent.getRole() != RoleType.EVM_STAFF) {
//            throw new InvalidRoleException(Constants.ErrorCode.INVALID_ROLE_TYPE, parent.getRole());
//        }
//
//        // No permission for other admin to create admin account
//        if (userPostDto.role() == RoleType.EVM_ADMIN) {
//            throw new InvalidRoleException(Constants.ErrorCode.INVALID_ROLE_TYPE, userPostDto.role());
//        }
//
//        // Validate allowed roles
//        if (userPostDto.role() != RoleType.EVM_STAFF &&
//                userPostDto.role() != RoleType.DEALER_MANAGER &&
//                userPostDto.role() != RoleType.DEALER_STAFF) {
//            throw new InvalidRoleException(Constants.ErrorCode.INVALID_ROLE_TYPE, userPostDto.role());
//        }
//
//        User child = createUserUnderParent(parent, userPostDto);
//        parent.getChildren().add(child);
//        userRepository.save(parent);
//
//        return UserDetailGetDto.fromModel(child);
//    }

    public UserDetailGetDto createEvdAccount(UserPostDto userPostDto) {

        User currentUser = getCurrentUser();

        if (currentUser.getRole() == RoleType.DEALER_STAFF
                && userPostDto.role() == RoleType.EVM_ADMIN) {
            throw new NoPermissionException("Dealer staff cannot create EVM admin accounts");
        }

        String hashedPassword = passwordEncoder.encode(userPostDto.password());

        User user = User.builder()
                .dealerInfo(null)
                .username(userPostDto.username())
                .hashedPassword(hashedPassword)
                .fullName(userPostDto.fullName())
                .address(userPostDto.city())
                .email(userPostDto.email())
                .phone(userPostDto.phone())
                .role(userPostDto.role())
                .isActive(userPostDto.isActive())
                .build();

        return UserDetailGetDto.fromModel(userRepository.save(user));
    }

    @Transactional
    public DealerInfoGetDto registryDealerInfo(DealerInfoPostDto dealerInfoPostDto, DealerUserPostDto newDealerDto) {

        User currentManufacturer = getCurrentUser();

        DealerHierarchy dealerHierarchy = dealerHierarchyRepository.findById(dealerInfoPostDto.dealerLevel())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_HIERARCHY_NOT_FOUND));

        // 1. Create DealerInfo
        DealerInfo dealerInfo = new DealerInfo();
        dealerInfo.setDealerName(dealerInfoPostDto.dealerName());
        dealerInfo.setDealerHierarchy(dealerHierarchy);
        dealerInfo.setLocation(dealerInfoPostDto.location());

        dealerInfo = dealerInfoRepository.save(dealerInfo);

        // 2. Create Dealer User and assign dealerInfo
        User savedDealerUser = createInternalDealerAccount(newDealerDto, dealerInfo);

        // 3. Create Contract
        try {
            dealerInfo = registryFileGenerator.generateContract(
                    currentManufacturer, savedDealerUser, dealerInfo
            );
        } catch (IOException | DocumentException exception) {
            log.error("Failed to generate contract for dealer: {}", dealerInfoPostDto.dealerName(), exception);
            throw new RuntimeException("Contract generation failed", exception);
        }

        return DealerInfoGetDto.fromModel(dealerInfo);
    }

    public UserDetailGetDto createDealerAccount(DealerUserPostDto dealerUserPostDto) {
        DealerInfo dealerInfo = getDealerInfoById(dealerUserPostDto.dealerInfoId());
        User user = createInternalDealerAccount(dealerUserPostDto, dealerInfo);
        return UserDetailGetDto.fromModel(userRepository.save(user));
    }

    private User createInternalDealerAccount(DealerUserPostDto dealerUserPostDto, DealerInfo dealerInfo) {
        String hashedPassword = passwordEncoder.encode(dealerUserPostDto.password());
        User user = User.builder()
                .dealerInfo(dealerInfo)
                .username(dealerUserPostDto.username())
                .hashedPassword(hashedPassword)
                .fullName(dealerUserPostDto.fullName())
                .address(dealerUserPostDto.city())
                .email(dealerUserPostDto.email())
                .phone(dealerUserPostDto.phone())
                .role(dealerUserPostDto.role())
                .isActive(dealerUserPostDto.isActive())
                .build();

        dealerInfo.getUsers().add(user);

        return userRepository.save(user);
    }

    public void updateUserInfo(Long userId, UserUpdateDto userUpdateDto) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.USER_NOT_FOUND, userId));

        if (userUpdateDto.fullName() != null
                && !userUpdateDto.fullName().isBlank()
                && !userUpdateDto.fullName().equals(existingUser.getFullName()))
            existingUser.setFullName(userUpdateDto.fullName());

        if (userUpdateDto.email() != null
                && !userUpdateDto.email().isBlank()
                && !userUpdateDto.email().equals(existingUser.getEmail())) {
            validateEmail(userUpdateDto.email());
            existingUser.setEmail(userUpdateDto.email());
        }

        if (userUpdateDto.phone() != null
                && !userUpdateDto.phone().isBlank()
                && !userUpdateDto.phone().equals(existingUser.getPhone())) {
            validatePhoneNumber(userUpdateDto.phone());
            existingUser.setPhone(userUpdateDto.phone());
        }

        if (userUpdateDto.city() != null
                && !userUpdateDto.city().equals(existingUser.getAddress())) {
            existingUser.setAddress(userUpdateDto.city());
        }

        userRepository.save(existingUser);
    }

    public void rankDealer(Long dealerInfoId, Integer newLevel) {

        DealerInfo existingDealer = getDealerInfoById(dealerInfoId);

        if (newLevel < 1 || newLevel > 3)
            throw new IllegalArgumentException("Invalid dealer level: " + newLevel);

        DealerHierarchy dealerHierarchy = dealerHierarchyRepository.findById(newLevel.longValue())
                        .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_HIERARCHY_NOT_FOUND));

        existingDealer.setDealerHierarchy(dealerHierarchy);
        dealerInfoRepository.save(existingDealer);
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

    private UserInfoListDto toUserInfoListDto(Page<User> userPage) {
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

    private DealerInfo getDealerInfoById(Long dealerId) {
        return dealerInfoRepository.findById(dealerId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_INFO_NOT_FOUND, dealerId));
    }

    public User getDealerByDealerPhone(String phoneNumber) {
        return userRepository.findByPhone(phoneNumber)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_WITH_PHONE_NUMBER_NOT_EXIST, phoneNumber));
    }

}
