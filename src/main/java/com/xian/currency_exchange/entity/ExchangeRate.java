package com.xian.currency_exchange.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baseCurrency;

    @Column(precision=10, scale=4)
    private BigDecimal buyRate;

    @Column(precision=10, scale=4)
    private BigDecimal sellRate;

    private Timestamp lastUpdated;

    private String source;

    @Column(unique = true)
    private String targetCode;
}
