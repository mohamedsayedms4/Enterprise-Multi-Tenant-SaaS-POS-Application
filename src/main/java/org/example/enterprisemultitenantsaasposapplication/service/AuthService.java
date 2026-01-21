package org.example.enterprisemultitenantsaasposapplication.service;

import org.example.enterprisemultitenantsaasposapplication.exception.UserException;
import org.example.enterprisemultitenantsaasposapplication.payload.request.LoginRequest;
import org.example.enterprisemultitenantsaasposapplication.payload.request.UserDto;
import org.example.enterprisemultitenantsaasposapplication.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest) throws UserException;
    AuthResponse register(UserDto userDto) throws UserException;
}
