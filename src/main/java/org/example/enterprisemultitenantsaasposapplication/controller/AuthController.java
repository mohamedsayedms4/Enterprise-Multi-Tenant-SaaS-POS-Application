package org.example.enterprisemultitenantsaasposapplication.controller;

import lombok.RequiredArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.exception.UserException;
import org.example.enterprisemultitenantsaasposapplication.payload.request.LoginRequest;
import org.example.enterprisemultitenantsaasposapplication.payload.request.UserDto;
import org.example.enterprisemultitenantsaasposapplication.payload.response.AuthResponse;
import org.example.enterprisemultitenantsaasposapplication.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDto userDto) throws UserException {
        AuthResponse response = authService.register(userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws UserException {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
