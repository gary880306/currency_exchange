package com.xian.currency_exchange.controller;

import com.xian.currency_exchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/currency")
    public ResponseEntity<?> getBaseCurrency() {
        List<String> currencies = exchangeRateService.findAllDistinctTargetCodes();
        currencies.add("TWD");
        return ResponseEntity.ok(currencies);
    }

//    @PostMapping("/convert")
//    public ResponseEntity<?> convertCurrency(@RequestParam String baseCurrency, @RequestParam double baseAmount, @RequestParam String targetCurrency) {
//        // 貨幣匯率計算
//        double convertedAmount = exchangeRateService.convertCurrency(baseCurrency, baseAmount, targetCurrency);
//
//        // 創建金額轉換對象
//        Map<String, Double> response = new HashMap<>();
//        response.put("convertedAmount", convertedAmount);
//
//        return ResponseEntity.ok(response);
//    }
}
