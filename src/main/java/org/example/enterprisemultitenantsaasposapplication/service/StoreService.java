package org.example.enterprisemultitenantsaasposapplication.service;

public interface StoreService {

    StoreDTO createStore(StoreDTO storeDTO, User user);
    StoreDTO getStoreById(Long id);
    List<StoreDTO> getAllStores();
    Store getStoreByAdmin();
    StoreDTO updateStore(Long id, StoreDTO storeDTO);
    StoreDTO deleteStore(Long id);
    StoreDTO getStoreByEmployee();
}
