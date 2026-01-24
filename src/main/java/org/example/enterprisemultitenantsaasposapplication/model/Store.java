package org.example.enterprisemultitenantsaasposapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        name = "stores",
        indexes = {
                @Index(name = "idx_stores_brand", columnList = "brand"),
                @Index(name = "idx_stores_status", columnList = "store_status"),
                @Index(name = "idx_stores_admin", columnList = "store_admin_id")
        }
)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String brand;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_admin_id", nullable = false)
    private User storeAdmin;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_status", nullable = false, length = 20)
    private StoreStatus storeStatus;

    @Embedded
    private StoreContact contact = new StoreContact();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "store_employees",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            indexes = {
                    @Index(name = "idx_store_employees_store", columnList = "store_id"),
                    @Index(name = "idx_store_employees_user", columnList = "user_id")
            }
    )
    private Set<User> employees = new HashSet<>();
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.created = now;
        this.updated = now;

        if (this.storeStatus == null) {
            this.storeStatus = StoreStatus.PENDING;
        }
        if (this.contact == null) {
            this.contact = new StoreContact();
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }
}
