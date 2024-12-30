package com.codingshuttle.securityapp.dtos;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionDto {
    private String user;
    @Positive
    private final Integer sessionCount;
}
