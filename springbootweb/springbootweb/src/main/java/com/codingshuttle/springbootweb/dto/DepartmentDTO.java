package com.codingshuttle.springbootweb.dto;

import com.codingshuttle.springbootweb.annotations.ValidPassword;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
@Data
public class DepartmentDTO {
    private Long id;
//    @NotBlank(message = "Required Field, Department Title cannot be blank")
    private String title;
    @AssertTrue(message = "Department must be active")
    private Boolean isActive;
    @PastOrPresent(message = "Department Field createdOn must be a day in the past or Present")
    private LocalDate createdOn;
    @ValidPassword
    private String password;
}
