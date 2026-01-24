package org.example.enterprisemultitenantsaasposapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.domain.UserRole;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

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
