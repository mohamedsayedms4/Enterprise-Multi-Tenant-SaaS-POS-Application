package org.example.enterprisemultitenantsaasposapplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class StoreContact {

    @Column(length = 255)
    private String address;

    @Column(length = 30)
    private String phone;

    @Column(length = 150)
    private String email;
}
