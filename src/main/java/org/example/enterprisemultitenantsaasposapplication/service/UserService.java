package org.example.enterprisemultitenantsaasposapplication.service;

import org.example.enterprisemultitenantsaasposapplication.payload.response.UserDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDtoResponse getUserFromJwtToken(String token);
    UserDtoResponse getCurrentUser();
    UserDtoResponse getUserByEmail(String email);
    UserDtoResponse getUserById(Long id);
    Page<UserDtoResponse> getAllUsers(Pageable pageable);
}
