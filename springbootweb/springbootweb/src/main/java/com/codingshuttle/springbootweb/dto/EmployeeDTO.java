package com.codingshuttle.springbootweb.dto;

import com.codingshuttle.springbootweb.annotations.EmailNotValid;
import com.codingshuttle.springbootweb.annotations.ValidPrimeNumber;
import com.codingshuttle.springbootweb.util.TrimDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    Long id;
    @NotBlank(message = "Required Field in Employee : name cannot be blank")
    @Size(min = 3, max = 10, message = "Field name must be in the range [3,10]")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Field Name can have only letters from a-z | A-Z and spaces")
    @JsonDeserialize(using = TrimDeserializer.class)
    String name;
    @NotBlank(message = "Required Field in Employee : email cannot be blank ")
//    @Email(message = "Email Field must have a valid email")
    @EmailNotValid
    String email;
    @Max(value = 60, message = "Age of Employee cannot be greater than 60")
    @Min(value = 18, message = "Age of an Employee cannot be less than 18")
    Integer age;
    @Past(message = "dateOfJoining must be a day in the past")
    LocalDate dateOfJoining;
    @JsonProperty("isActive")
    @AssertTrue(message = "Employee has to be active")
    Boolean isActive;
    @Positive(message = "salary should be positive")
//  @Digits(integer = 6,fraction = 2, message = "Salary can have upto 6 integer and 2 fractional digits")
    @DecimalMin(value = "100.50", message = "Salary cannot be less than 100.50")
    @DecimalMax(value = "999999.99", message = "Salary cannot be more than 999999.99")
    BigDecimal salary;
    @ValidPrimeNumber
    Integer primeNumber;

}
