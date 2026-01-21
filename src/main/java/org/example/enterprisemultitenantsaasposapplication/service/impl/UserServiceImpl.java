package org.example.enterprisemultitenantsaasposapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.enterprisemultitenantsaasposapplication.commoon.MessageUtil;
import org.example.enterprisemultitenantsaasposapplication.configuration.JwtProvider;
import org.example.enterprisemultitenantsaasposapplication.exception.BadException;
import org.example.enterprisemultitenantsaasposapplication.exception.NotFoundException;
import org.example.enterprisemultitenantsaasposapplication.mapper.UserMapper;
import org.example.enterprisemultitenantsaasposapplication.model.User;
import org.example.enterprisemultitenantsaasposapplication.payload.response.UserDtoResponse;
import org.example.enterprisemultitenantsaasposapplication.repository.UserRepository;
import org.example.enterprisemultitenantsaasposapplication.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final MessageUtil msg;

    @Override
    public UserDtoResponse getUserFromJwtToken(String token) {
        String email = jwtProvider.getEmail(token);

        Optional<UserDtoResponse> user = userRepository.findDtoByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new BadException(msg.get("auth.token.invalid"));
        }
    }

    // todo
    @Override
    public UserDtoResponse getCurrentUser() {
        return null;
    }

    @Override
    public UserDtoResponse getUserByEmail(String email) {
        return userRepository.findDtoByEmail(email)
                .orElseThrow(() -> new NotFoundException(msg.get("user.not.found")));
    }

    @Override
    public UserDtoResponse getUserById(Long id) {
        return userRepository.findDtoById(id)
                .orElseThrow(() -> new NotFoundException(msg.get("user.not.found")));
    }

    @Override
    public Page<UserDtoResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAllDto(pageable);
    }
}
