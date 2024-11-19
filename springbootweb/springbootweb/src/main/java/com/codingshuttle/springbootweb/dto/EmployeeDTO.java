package com.codingshuttle.springbootweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    Long id;
    String name;
    String email;
    Integer age;
    LocalDate dateOfJoining;
    @JsonProperty("isActive")
    Boolean isActive;

}
