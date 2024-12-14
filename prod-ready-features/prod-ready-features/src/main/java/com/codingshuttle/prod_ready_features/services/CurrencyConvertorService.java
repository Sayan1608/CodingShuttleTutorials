package com.codingshuttle.prod_ready_features.services;

import com.codingshuttle.prod_ready_features.clients.CurrencyRestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyConvertorService {
    private final CurrencyRestClient currencyRestClient;

    public CurrencyConvertorService(CurrencyRestClient currencyRestClient) {
        this.currencyRestClient = currencyRestClient;
    }


    public BigDecimal convertCurrency(String fromCurrency, String toCurrency, BigDecimal units){
        String jsonResponse = currencyRestClient
                .convertCurrency(fromCurrency, toCurrency);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(jsonResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode dataNode = rootNode.get("data");
        double usdValue = dataNode.get(toCurrency).asDouble();

        return BigDecimal.valueOf(usdValue * units.doubleValue()).setScale(2, RoundingMode.HALF_UP);
    }
}
