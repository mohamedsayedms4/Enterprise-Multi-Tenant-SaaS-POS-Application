package org.example.enterprisemultitenantsaasposapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.model.StoreStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponseDto {

    private Long id;
    private String brand;
    private String description;

    private StoreStatus storeStatus;

    private StoreContactDto contact;

    private UserSummaryDto storeAdmin;

    private LocalDateTime created;
    private LocalDateTime updated;
}
