package org.example.enterprisemultitenantsaasposapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.model.StoreStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreUpdateRequestDto {

    private String brand;
    private String description;

    /**
     * لو حابب تغير الأدمن
     */
    private Long storeAdminId;

    /**
     * لو حابب تغير حالة المتجر
     */
    private StoreStatus storeStatus;

    private StoreContactDto contact;
}
