package org.example.enterprisemultitenantsaasposapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateRequestDto {

    private String brand;
    private String description;

    /**
     * نمرر ID فقط بدل ما نمرر User كامل
     */
    private Long storeAdminId;

    private StoreContactDto contact;
}
