package org.example.enterprisemultitenantsaasposapplication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.enterprisemultitenantsaasposapplication.domain.UserRole;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDtoResponse {
    private Long id;
    private String fullName;
    private String email;
    private UserRole role;
    private boolean enabled;
    private String phone;
    private LocalDateTime lastLogin;
    private LocalDateTime created;
    private LocalDateTime updated;
}
