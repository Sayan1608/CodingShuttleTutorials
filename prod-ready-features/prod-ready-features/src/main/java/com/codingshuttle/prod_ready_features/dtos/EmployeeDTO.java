package com.codingshuttle.prod_ready_features.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {
    Long id;

    String name;

    String email;

    Integer age;

    LocalDate dateOfJoining;

    Boolean isActive;

    BigDecimal salary;

    Integer primeNumber;

}
