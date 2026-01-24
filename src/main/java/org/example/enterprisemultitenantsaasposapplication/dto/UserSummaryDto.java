package org.example.enterprisemultitenantsaasposapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.domain.UserRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {
    private Long id;
    private String fullName;
    private String email;
    private UserRole role;
    private boolean enabled;
    private String phone;
}
