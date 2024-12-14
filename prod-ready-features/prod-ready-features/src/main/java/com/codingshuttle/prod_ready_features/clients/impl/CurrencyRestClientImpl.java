package com.codingshuttle.prod_ready_features.clients.impl;

import com.codingshuttle.prod_ready_features.clients.CurrencyRestClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
@Service
public class CurrencyRestClientImpl implements CurrencyRestClient {

    private final RestClient cuurencyRestClient;

    public CurrencyRestClientImpl(@Qualifier("currencyRestClient") RestClient cuurencyRestClient) {
        this.cuurencyRestClient = cuurencyRestClient;
    }

    @Override
    public String convertCurrency(String fromCurrency, String toCurrency) {
        try {
            return cuurencyRestClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("currencies",toCurrency)
                            .queryParam("base_currency",fromCurrency)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
