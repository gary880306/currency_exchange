// 使用AJAX調用/currency API獲取數據
$.ajax({
    url: '/currency',
    type: 'GET',
    success: function(data) {
        // 循環便利數據
        data.forEach(function(currency) {
            // 創建一個新的選項
            var option = $("<option></option>").attr("value", currency).text(currency);

            // 將新的選項添加到下拉式選單
            $("#baseCurrencySelect").append(option.clone());
            $("#targetCurrencySelect").append(option);
        });
    },
    error: function(error) {
        console.error('Error:', error);
    }
});


$(document).ready(function() {
    // 當選擇基礎貨幣或輸入金額時觸發
    $("#baseCurrencySelect, #baseCurrencyInput").on("change input", function() {
        // 取得用戶選擇的基礎貨幣和輸入的金額
        var baseCurrency = $("#baseCurrencySelect").val();
        var baseAmount = parseFloat($("#baseCurrencyInput").val());

        // 取得用戶選擇的目標貨幣
        var targetCurrency = $("#targetCurrencySelect").val();

        // 發送AJAX請求到後端API進行貨幣轉換
        $.ajax({
            url: '/convert',
            type: 'POST',
            data: {
                baseCurrency: baseCurrency,
                baseAmount: baseAmount,
                targetCurrency: targetCurrency
            },
            success: function(data) {
                // 將轉換後的金額渲染到目標貨幣輸入框
                $("#targetCurrencyInput").val(data.convertedAmount.toFixed(2)); // 假設後端回傳的是轉換後的金額
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    });
});


