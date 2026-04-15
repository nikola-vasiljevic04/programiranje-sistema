package org.raflab.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.raflab.authservice.dto.LoginDTO;
import org.raflab.authservice.dto.RoleReqDTO;
import org.raflab.authservice.dto.UserDTO;
import org.raflab.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operations related to authentication")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email is already in use")
    })
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO request) {
        log.info("Registration attempt for email: {}", request.getEmail());
        UserDTO response = authService.createUser(request);
        log.info("User successfully registered with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    @ApiResponse(responseCode = "200", description = "Logged in", content = @Content)
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO request) {
        log.info("Login attempt for user: {}", request.getEmail());
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @GetMapping("/validate")
    @Operation(summary = "Validate API Key for Gateway")
    public ResponseEntity<UserDTO> validate(@RequestHeader("X-API-KEY") String key) {
        log.info("Gateway is validating API Key: {}...", key.substring(0, 5));

        UserDTO user = authService.validateKey(key);
        log.info("Key validated. User: {}, Roles: {}", user.getEmail(), user.getRoles());
        return ResponseEntity.ok(user);    }

    @DeleteMapping("/logout")
    @Operation(summary = "Logout user")
    @ApiResponse(responseCode = "204", description = "Logged out")
    public ResponseEntity<Void> logout(@RequestHeader("X-API-KEY") String key) {
        log.info("Logout request received for API Key: {}...", key.substring(0, 5));
        authService.logoutUser(key);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/role")
    @Operation(summary = "Change user role")
    public ResponseEntity<UserDTO> changeRole(
            @RequestBody @Valid RoleReqDTO request,
            @RequestHeader("X-API-KEY") String apiKey) {
        log.warn("Role change requested for UserID: {} by Admin with Key: {}...", request.getUserId(), apiKey.substring(0, 5));
        return ResponseEntity.ok(authService.changeRoles(request, apiKey));
    }
}