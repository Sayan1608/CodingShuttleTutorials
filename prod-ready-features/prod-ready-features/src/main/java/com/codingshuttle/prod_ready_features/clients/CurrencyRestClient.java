package com.codingshuttle.prod_ready_features.clients;

import java.math.BigDecimal;

public interface CurrencyRestClient {
    public String convertCurrency(String fromCurrency, String toCurrency);
}
