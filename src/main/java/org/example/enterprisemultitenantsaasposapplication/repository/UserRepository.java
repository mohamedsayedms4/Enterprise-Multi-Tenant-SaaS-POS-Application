package org.example.enterprisemultitenantsaasposapplication.repository;

import org.example.enterprisemultitenantsaasposapplication.model.User;
import org.example.enterprisemultitenantsaasposapplication.payload.response.UserDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("""
        select new org.example.enterprisemultitenantsaasposapplication.payload.response.UserDtoResponse(
            u.id, u.fullName, u.email, u.role, u.enabled, u.phone, u.lastLogin, u.created, u.updated
        )
        from User u
        where u.id = :id
    """)
    Optional<UserDtoResponse> findDtoById(@Param("id") Long id);

    @Query("""
        select new org.example.enterprisemultitenantsaasposapplication.payload.response.UserDtoResponse(
            u.id, u.fullName, u.email, u.role, u.enabled, u.phone, u.lastLogin, u.created, u.updated
        )
        from User u
        where u.email = :email
    """)
    Optional<UserDtoResponse> findDtoByEmail(@Param("email") String email);

    @Query("""
        select new org.example.enterprisemultitenantsaasposapplication.payload.response.UserDtoResponse(
            u.id, u.fullName, u.email, u.role, u.enabled, u.phone, u.lastLogin, u.created, u.updated
        )
        from User u
    """)
    Page<UserDtoResponse> findAllDto(Pageable pageable);
}
