package com.freightfox.demo.domain;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Items {

    private String name;

    private String quantity;

    private BigDecimal rate;

    private BigDecimal amount;

}
