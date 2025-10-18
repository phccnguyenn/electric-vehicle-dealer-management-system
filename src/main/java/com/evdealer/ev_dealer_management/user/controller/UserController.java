package com.evdealer.ev_dealer_management.user.controller;

import com.evdealer.ev_dealer_management.user.model.dto.UserDetailGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.UserInfoListDto;
import com.evdealer.ev_dealer_management.user.model.dto.UserPostDto;
import com.evdealer.ev_dealer_management.user.model.dto.UserUpdateDto;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/filter-by-role")
    public ResponseEntity<UserInfoListDto> getAllUserProfile(
            @RequestParam(name = "role", defaultValue = "EVM_STAFF", required = false) RoleType roleType,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(userService.getAllUser(pageNo, pageSize, roleType));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDetailGetDto> createUser(@RequestBody UserPostDto userPostDto) {
        UserDetailGetDto createdUser = userService.userCreationByAdmin(userPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PatchMapping("/{userId}/update")
    public ResponseEntity<Void> updatePartialUser(
            @PathVariable(name = "userId") Long userId,
            @RequestBody UserUpdateDto userUpdateDto
    ) {
        userService.updatePartialUser(userId, userUpdateDto);
        return ResponseEntity.noContent().build();
    }


}
