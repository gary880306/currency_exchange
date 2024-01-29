package com.xian.currency_exchange.service;

import com.xian.currency_exchange.entity.ExchangeRate;
import com.xian.currency_exchange.repository.ExchangeRateRepository;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExchangeRateScraper {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @PostConstruct
    @Scheduled(fixedRate = 600000) // 600000 毫秒 = 10 分鐘
    public void scrapeExchangeRates() {
        try {
            String url = "https://rate.bot.com.tw/xrt?Lang=zh-TW";
            Document doc = Jsoup.connect(url).get();

            Elements currencyRows = doc.select("tr");
            for (Element row : currencyRows) {
                String currencyName = row.select("td.currency").text();
                if (!currencyName.isEmpty()) {
                    String currencyCode = extractCurrencyCode(currencyName); // 提取貨幣代碼

                    BigDecimal sightBuyRate = parseRate(extractRateText(row, "本行即期買入"));
                    BigDecimal sightSellRate = parseRate(extractRateText(row, "本行即期賣出"));

                    // 檢查是否已存在該貨幣代碼的紀錄
                    ExchangeRate exchangeRate = exchangeRateRepository.findByCurrencyCode(currencyCode)
                            .orElse(new ExchangeRate());  // 如果不存在則創建新實體

                    exchangeRate.setCurrencyCode(currencyCode);
                    exchangeRate.setBaseCurrency("TWD");
                    exchangeRate.setBuyRate(sightBuyRate);
                    exchangeRate.setSellRate(sightSellRate);
                    exchangeRate.setLastUpdated(Timestamp.from(Instant.now()));
                    exchangeRate.setSource("臺灣銀行");

                    exchangeRateRepository.save(exchangeRate); // 更新或保存紀錄
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String extractCurrencyCode(String currencyText) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(currencyText);
        if (matcher.find()) {
            return matcher.group(1);  // 提取括號內的文本
        }
        return "";  // 如果沒有匹配，返回空字符串
    }
    private String extractRateText(Element row, String dataTableName) {
        Element rateElement = row.selectFirst("td[data-table=" + dataTableName + "]");
        return rateElement != null ? rateElement.text() : "無資料";
    }

    private BigDecimal parseRate(String rateStr) {
        try {
            return new BigDecimal(rateStr.replace(",", ""));
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
}
