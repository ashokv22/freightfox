package com.freightfox.demo.domain;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Invoice {

    private String seller;

    private String sellerGstin;

    private String sellerAddress;

    private String buyer;

    private String buyerGstin;

    private String buyerAddress;

    private List<Items> items;

}
