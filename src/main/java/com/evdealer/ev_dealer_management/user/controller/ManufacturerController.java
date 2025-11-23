package com.evdealer.ev_dealer_management.user.controller;

import com.evdealer.ev_dealer_management.user.model.dto.account.UserPostDto;
import com.evdealer.ev_dealer_management.user.model.dto.dealer.DealerInfoGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.dealer.DealerRegistryDto;
import com.evdealer.ev_dealer_management.user.model.dto.dealer.DealerUserPostDto;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserDetailGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserInfoListDto;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserUpdateDto;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.service.ManufacturerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Users management APIs")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping("/all")
    public ResponseEntity<UserInfoListDto> getAllUsers(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return ResponseEntity.ok(manufacturerService.getAllUsers(pageNo, pageSize));
    }

    @GetMapping("/filter-by-role")
    public ResponseEntity<UserInfoListDto> getAllUserProfile(
            @RequestParam(name = "role", defaultValue = "EVM_STAFF", required = false) RoleType roleType,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(manufacturerService.getAllUsersByRole(pageNo, pageSize, roleType));
    }

    @PostMapping("/registry-dealer")
    public ResponseEntity<DealerInfoGetDto> registryDealerInfo(@RequestBody DealerRegistryDto dealerInfoPostDto) {
        return ResponseEntity.ok(
                manufacturerService.registryDealerInfo(
                        dealerInfoPostDto.dealerInfo(),
                        dealerInfoPostDto.dealerAccount()
                )
        );
    }

    @PostMapping("/create-dealer-account")
    public ResponseEntity<UserDetailGetDto> createDealerAccount(@RequestBody @Valid DealerUserPostDto dealerUserPostDto) {
        UserDetailGetDto createdUser = manufacturerService.createDealerAccount(dealerUserPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/create-evd-account")
    public ResponseEntity<UserDetailGetDto> createEvdAccount(@RequestBody @Valid UserPostDto userPostDto) {
        UserDetailGetDto createdUser = manufacturerService.createEvdAccount(userPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestParam(name = "currentPassword") String currentPassword,
            @RequestParam(name = "newPassword") String newPassword) {
        manufacturerService.changePassword(currentPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/update")
    public ResponseEntity<Void> updatePartialUser(
            @PathVariable(name = "userId") Long userId,
            @RequestBody UserUpdateDto userUpdateDto
    ) {
        manufacturerService.updateUserInfo(userId, userUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/hierarchy/{dealerId}")
    public ResponseEntity<Void> rankDealer(
            @PathVariable(name = "dealerId") Long dealerId,
            @RequestParam(name = "level") Integer level
    ) {
        manufacturerService.rankDealer(dealerId, level);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/ban")
    public ResponseEntity<Void> banUserById(@PathVariable(name = "userId") Long userId) {
        manufacturerService.UserBanManagement(userId, true);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/unban")
    public ResponseEntity<Void> unbanUserById(@PathVariable(name = "userId") Long userId) {
        manufacturerService.UserBanManagement(userId, false);
        return ResponseEntity.noContent().build();
    }

}
