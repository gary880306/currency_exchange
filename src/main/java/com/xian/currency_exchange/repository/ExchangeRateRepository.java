package com.xian.currency_exchange.repository;

import com.xian.currency_exchange.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    // 唯一識別碼
    Optional<ExchangeRate> findByTargetCode(String targetCode);
    @Query("SELECT DISTINCT ce.targetCode FROM ExchangeRate ce")
    List<String> findAllDistinctTargetCodes();
}
