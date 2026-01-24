package org.example.enterprisemultitenantsaasposapplication.repository;

import org.example.enterprisemultitenantsaasposapplication.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByStoreAdmin_Id(Long adminId);

    @Query("""
        select s
        from Store s
        join s.employees e
        where e.id = :employeeId
    """)
    List<Store> findByEmployeeId(@Param("employeeId") Long employeeId);
}
