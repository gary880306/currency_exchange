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
    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private Timestamp lastUpdated;
    private String source;
    @Column(unique = true)
    private String currencyCode;
}
