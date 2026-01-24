package org.example.enterprisemultitenantsaasposapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.dto.*;
import org.example.enterprisemultitenantsaasposapplication.exception.ResourceNotFoundException;
import org.example.enterprisemultitenantsaasposapplication.model.Store;
import org.example.enterprisemultitenantsaasposapplication.model.StoreContact;
import org.example.enterprisemultitenantsaasposapplication.model.User;
import org.example.enterprisemultitenantsaasposapplication.repository.StoreRepository;
import org.example.enterprisemultitenantsaasposapplication.repository.UserRepository;
import org.example.enterprisemultitenantsaasposapplication.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    public StoreResponseDto createStore(StoreCreateRequestDto request) {
        User admin = userRepository.findById(request.getStoreAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("User (Admin) not found with id: " + request.getStoreAdminId()));

        Store store = new Store();
        store.setBrand(request.getBrand());
        store.setDescription(request.getDescription());
        store.setStoreAdmin(admin);

        if (request.getContact() != null) {
            store.setContact(toContactEmbeddable(request.getContact()));
        }

        Store saved = storeRepository.save(store);
        return toStoreResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreResponseDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));
        return toStoreResponseDto(store);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreResponseDto> getAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(this::toStoreResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStoreByAdmin(Long adminId) {
        return storeRepository.findByStoreAdmin_Id(adminId)
                .stream()
                .map(this::toStoreResponseDto)
                .toList();
    }

    @Override
    public StoreResponseDto updateStore(Long storeId, StoreUpdateRequestDto request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));

        if (request.getBrand() != null && !request.getBrand().isBlank()) {
            store.setBrand(request.getBrand());
        }

        if (request.getDescription() != null) {
            store.setDescription(request.getDescription());
        }

        if (request.getStoreStatus() != null) {
            store.setStoreStatus(request.getStoreStatus());
        }

        if (request.getStoreAdminId() != null) {
            User newAdmin = userRepository.findById(request.getStoreAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("User (Admin) not found with id: " + request.getStoreAdminId()));
            store.setStoreAdmin(newAdmin);
        }

        if (request.getContact() != null) {
            store.setContact(toContactEmbeddable(request.getContact()));
        }

        Store saved = storeRepository.save(store);
        return toStoreResponseDto(saved);
    }

    @Override
    public void deleteStore(Long storeId) {
        if (!storeRepository.existsById(storeId)) {
            throw new ResourceNotFoundException("Store not found with id: " + storeId);
        }
        storeRepository.deleteById(storeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStoreByEmployee(Long employeeId) {
        return storeRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::toStoreResponseDto)
                .toList();
    }

    // -------------------------
    // Manual mapping (بدون MapStruct)
    // -------------------------

    private StoreResponseDto toStoreResponseDto(Store store) {
        return StoreResponseDto.builder()
                .id(store.getId())
                .brand(store.getBrand())
                .description(store.getDescription())
                .storeStatus(store.getStoreStatus())
                .contact(toStoreContactDto(store.getContact()))
                .storeAdmin(toUserSummaryDto(store.getStoreAdmin()))
                .created(store.getCreated())
                .updated(store.getUpdated())
                .build();
    }

    private UserSummaryDto toUserSummaryDto(User user) {
        if (user == null) return null;
        return UserSummaryDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .phone(user.getPhone())
                .build();
    }

    private StoreContactDto toStoreContactDto(StoreContact contact) {
        if (contact == null) return null;
        return StoreContactDto.builder()
                .address(contact.getAddress())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .build();
    }

    private StoreContact toContactEmbeddable(StoreContactDto dto) {
        StoreContact contact = new StoreContact();
        contact.setAddress(dto.getAddress());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        return contact;
    }
}
