package com.codingshuttle.caching.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EmployeeDto implements Serializable {
    private Long id;
    @NotBlank
    private String name;

    @Email(message = "Please enter valid employee email id")
    @NotBlank(message = "Please provide employee email id")
    private String email;

    @Positive(message = "salary has to be a positive number")
    private BigDecimal salary;
}
