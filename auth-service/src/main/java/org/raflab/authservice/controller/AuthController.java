package org.raflab.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.raflab.authservice.dto.LoginDTO;
import org.raflab.authservice.dto.RoleReqDTO;
import org.raflab.authservice.dto.UserDTO;
import org.raflab.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operations related to authentication")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register user")
    @ApiResponse(responseCode = "200", description = "Registered")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO request) {
        return ResponseEntity.ok(authService.createUser(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    @ApiResponse(responseCode = "200", description = "Logged in")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @GetMapping("/validate")
    @Operation(summary = "Validate API Key for Gateway")
    public ResponseEntity<UserDTO> validate(@RequestHeader("X-API-KEY") String key) {
        return ResponseEntity.ok(authService.validateKey(key));
    }

    @DeleteMapping("/logout")
    @Operation(summary = "Logout user")
    @ApiResponse(responseCode = "204", description = "Logged out")
    public ResponseEntity<Void> logout(@RequestHeader("X-API-KEY") String key) {
        authService.logoutUser(key);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/role")
    @Operation(summary = "Change user role")
    public ResponseEntity<UserDTO> changeRole(
            @RequestBody @Valid RoleReqDTO request,
            @RequestHeader("X-API-KEY") String apiKey) {
        return ResponseEntity.ok(authService.changeRole(request, apiKey));
    }
}