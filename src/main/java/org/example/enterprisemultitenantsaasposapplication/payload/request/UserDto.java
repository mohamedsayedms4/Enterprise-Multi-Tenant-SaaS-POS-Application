package org.example.enterprisemultitenantsaasposapplication.payload.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.domain.UserRole;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String fullName;

    private String email;

    private String password;

    private UserRole role;

    private boolean enabled = true;

    private String phone;
    private LocalDateTime lastLogin;
    private LocalDateTime created;
    private LocalDateTime updated;
}
