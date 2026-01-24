package org.example.enterprisemultitenantsaasposapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreContactDto {
    private String address;
    private String phone;
    private String email;
}
