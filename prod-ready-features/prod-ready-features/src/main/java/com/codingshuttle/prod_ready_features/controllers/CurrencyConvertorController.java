package com.codingshuttle.prod_ready_features.controllers;

import com.codingshuttle.prod_ready_features.services.CurrencyConvertorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/currency-convertor")
public class CurrencyConvertorController {
    private final CurrencyConvertorService currencyConvertorService;

    public CurrencyConvertorController(CurrencyConvertorService currencyConvertorService) {
        this.currencyConvertorService = currencyConvertorService;
    }

    @GetMapping(path = "/convertCurrency")
    public BigDecimal convertCurrency(
            @RequestParam(name = "fromCurrency") String from,
            @RequestParam(name = "toCurrency") String to,
            @RequestParam BigDecimal units){
        return currencyConvertorService.convertCurrency(from,to,units);
    }
}
