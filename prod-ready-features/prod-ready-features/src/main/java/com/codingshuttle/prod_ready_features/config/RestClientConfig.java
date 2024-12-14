package com.codingshuttle.prod_ready_features.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class RestClientConfig {
    @Value("${currencyConvertor.base.url}")
    private  String CURRENCY_BASE_URL;
    @Value("${employeeService.base.url}")
    public String BASE_URL;

    @Bean
    @Qualifier("employeeRestClient")
    public RestClient getRestClient(){
        return RestClient
                .builder()
                .baseUrl(BASE_URL)
                .defaultHeader(CONTENT_TYPE,APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (req,res)->{
                    throw new RuntimeException("Server Error Occurred.");
                })
                .build();
    }

    @Bean
    @Qualifier("currencyRestClient")
    public RestClient getCurrencyRestClient(){
        return RestClient
                .builder()
                .baseUrl(CURRENCY_BASE_URL)
                .defaultHeader(CONTENT_TYPE,APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (req,res)->{
                    throw new RuntimeException("Server Error Occurred in free currency convertor.");
                })
                .build();
    }
}
