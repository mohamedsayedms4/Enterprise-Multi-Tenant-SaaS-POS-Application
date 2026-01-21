package org.example.enterprisemultitenantsaasposapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.commoon.MessageUtil;
import org.example.enterprisemultitenantsaasposapplication.configuration.JwtProvider;
import org.example.enterprisemultitenantsaasposapplication.domain.UserRole;
import org.example.enterprisemultitenantsaasposapplication.exception.ConflictError;
import org.example.enterprisemultitenantsaasposapplication.exception.UserException;
import org.example.enterprisemultitenantsaasposapplication.model.User;
import org.example.enterprisemultitenantsaasposapplication.payload.request.LoginRequest;
import org.example.enterprisemultitenantsaasposapplication.payload.request.UserDto;
import org.example.enterprisemultitenantsaasposapplication.payload.response.AuthResponse;
import org.example.enterprisemultitenantsaasposapplication.repository.UserRepository;
import org.example.enterprisemultitenantsaasposapplication.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final MessageUtil msg;

    @Override
    public AuthResponse login(LoginRequest loginRequest) throws UserException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateToken(authentication);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage(msg.get("auth.success"));
            authResponse.setToken(jwt);
            return authResponse;

        } catch (Exception ex) {
            throw new UserException(msg.get("auth.invalid"));
        }
    }

    @Override
    public AuthResponse register(UserDto userDto) throws UserException, ConflictError {

        User user = userRepository.findByEmail(userDto.getEmail()).orElse(null);

        if (user != null) {
            throw new ConflictError(msg.get("auth.email.exists"));
        }

        if (userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new UserException(msg.get("auth.role.admin.not.allowed"));
        }

        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setFullName(userDto.getFullName());
        newUser.setPhone(userDto.getPhone());
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setCreated(LocalDateTime.now());
        newUser.setUpdated(LocalDateTime.now());
        userRepository.save(newUser);

        // ✅ authenticate to load authorities via CustomerUserImpl
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage(msg.get("auth.success"));
        authResponse.setToken(jwt);
        return authResponse;
    }
}
