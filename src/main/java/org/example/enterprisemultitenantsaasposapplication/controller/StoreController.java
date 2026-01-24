package org.example.enterprisemultitenantsaasposapplication.controller;

import lombok.RequiredArgsConstructor;
import org.example.enterprisemultitenantsaasposapplication.dto.StoreCreateRequestDto;
import org.example.enterprisemultitenantsaasposapplication.dto.StoreResponseDto;
import org.example.enterprisemultitenantsaasposapplication.dto.StoreUpdateRequestDto;
import org.example.enterprisemultitenantsaasposapplication.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoreResponseDto createStore(@RequestBody StoreCreateRequestDto request) {
        return storeService.createStore(request);
    }

    @GetMapping("/{id}")
    public StoreResponseDto getStoreById(@PathVariable("id") Long id) {
        return storeService.getStoreById(id);
    }

    @GetMapping
    public List<StoreResponseDto> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/by-admin/{adminId}")
    public List<StoreResponseDto> getStoreByAdmin(@PathVariable Long adminId) {
        return storeService.getStoreByAdmin(adminId);
    }

    @GetMapping("/by-employee/{employeeId}")
    public List<StoreResponseDto> getStoreByEmployee(@PathVariable Long employeeId) {
        return storeService.getStoreByEmployee(employeeId);
    }

    @PutMapping("/{id}")
    public StoreResponseDto updateStore(@PathVariable("id") Long id,
                                        @RequestBody StoreUpdateRequestDto request) {
        return storeService.updateStore(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStore(@PathVariable("id") Long id) {
        storeService.deleteStore(id);
    }
}
