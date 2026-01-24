package org.example.enterprisemultitenantsaasposapplication.service;

import org.example.enterprisemultitenantsaasposapplication.dto.StoreCreateRequestDto;
import org.example.enterprisemultitenantsaasposapplication.dto.StoreResponseDto;
import org.example.enterprisemultitenantsaasposapplication.dto.StoreUpdateRequestDto;

import java.util.List;

public interface StoreService {

    StoreResponseDto createStore(StoreCreateRequestDto request);

    StoreResponseDto getStoreById(Long storeId);

    List<StoreResponseDto> getAllStores();

    List<StoreResponseDto> getStoreByAdmin(Long adminId);

    StoreResponseDto updateStore(Long storeId, StoreUpdateRequestDto request);

    void deleteStore(Long storeId);

    List<StoreResponseDto> getStoreByEmployee(Long employeeId);
}
