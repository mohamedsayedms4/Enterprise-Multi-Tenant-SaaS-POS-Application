package org.example.enterprisemultitenantsaasposapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand ;

    @OneToOne
    private User storeAdmin ;

    private String description;

    private LocalDateTime created;
    private LocalDateTime updated;


    private StoreStatus storeStatus ;


    @Embedded
    private StoreContact contact = new StoreContact();

    @PrePersist
    public void prePersist()
    {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
        this.storeStatus = StoreStatus.PENDING;
    }

    @PreUpdate
    public void preUpdate()
    {
        this.updated = LocalDateTime.now();
    }

}
