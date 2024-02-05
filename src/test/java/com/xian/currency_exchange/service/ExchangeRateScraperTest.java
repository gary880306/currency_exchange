package com.xian.currency_exchange.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExchangeRateScraperTest {
    @Test
    void scrapeExchangeRates() {
        try {
            String url = "https://rate.bot.com.tw/xrt?Lang=zh-TW"; // 爬蟲 URL
            Document doc = Jsoup.connect(url).get();
            assertNotNull(doc, "URL 獲取 Document");

            // 獲取 "本行即期買入" 的匯率
            Element buyRateElement = doc.selectFirst("td[data-table=本行即期買入]");
            String buyRate = buyRateElement != null ? buyRateElement.text() : "無法獲取資料";
            System.out.println("本行即期買入匯率: " + buyRate);

            // 獲取 "本行即期賣出" 的匯率
            Element sellRateElement = doc.selectFirst("td[data-table=本行即期賣出]");
            String sellRate = sellRateElement != null ? sellRateElement.text() : "無法獲取資料";
            System.out.println("本行即期賣出匯率: " + sellRate);

            // 選擇 tr
            Elements currencyRows = doc.select("tr");
            for (Element row : currencyRows) {
                // 爬取貨幣名稱
                String currencyName = row.select("td.currency").text();

                // 檢查貨幣是否有效
                if (!currencyName.isEmpty() && !currencyName.equals("美金 (USD)")) {
                    // 爬取資料，並檢查是否為 null
                    Element cashBuyRateElement = row.select("td[data-table=本行現金買入]").first();
                    String cashBuyRate = cashBuyRateElement != null ? cashBuyRateElement.text() : "無資料";

                    Element cashSellRateElement = row.select("td[data-table=本行現金賣出]").first();
                    String cashSellRate = cashSellRateElement != null ? cashSellRateElement.text() : "無資料";

                    Element sightBuyRateElement = row.select("td[data-table=本行即期買入]").first();
                    String sightBuyRate = sightBuyRateElement != null ? sightBuyRateElement.text() : "無資料";

                    Element sightSellRateElement = row.select("td[data-table=本行即期賣出]").first();
                    String sightSellRate = sightSellRateElement != null ? sightSellRateElement.text() : "無資料";

                    // 打印結果
                    System.out.println(currencyName + " - 現金買入: " + cashBuyRate + ", 現金賣出: " + cashSellRate + ", 即期買入: " + sightBuyRate + ", 即期賣出: " + sightSellRate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("URL抓取數據失敗，錯誤：" + e.getMessage());
        }
    }

}
