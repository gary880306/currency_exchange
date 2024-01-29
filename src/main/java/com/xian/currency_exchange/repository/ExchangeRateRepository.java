package com.xian.currency_exchange.repository;

import com.xian.currency_exchange.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    // 唯一識別碼
    Optional<ExchangeRate> findByCurrencyCode(String currencyCode);
}
