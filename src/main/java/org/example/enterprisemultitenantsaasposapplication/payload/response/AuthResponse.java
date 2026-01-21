package org.example.enterprisemultitenantsaasposapplication.payload.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String message;
}
