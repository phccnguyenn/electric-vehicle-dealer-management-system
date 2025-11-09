package com.evdealer.ev_dealer_management.auth.controller;

import com.evdealer.ev_dealer_management.auth.model.dto.AuthRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.AuthResponse;
import com.evdealer.ev_dealer_management.auth.service.AuthService;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserProfileGetDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth APIs")
public class AuthController {

    private final AuthService authService;

//    @GetMapping("/all/profile")
//    public ResponseEntity<UserInfoListDto> getAllUserProfile(
//            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return ResponseEntity.ok(authService.getAllUser(pageNo, pageSize));
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest authRequest
    ) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileGetDto> authenticate() {
        return ResponseEntity.ok(authService.authentication());
    }

//    @ApiResponses(
//        value = {
//            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = RegisterResponse.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
//            @ApiResponse(responseCode = "409", description = "Username already exists", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
//        }
//    )

//    @PostMapping("/register")
//    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest user) {
//        RegisterResponse response = authService.register(user);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

}
