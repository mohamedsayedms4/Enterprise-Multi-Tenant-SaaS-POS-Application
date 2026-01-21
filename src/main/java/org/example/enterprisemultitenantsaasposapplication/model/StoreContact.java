package org.example.enterprisemultitenantsaasposapplication.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class StoreContact {
    private String address ;
    private String phone ;
    private String email ;
}
