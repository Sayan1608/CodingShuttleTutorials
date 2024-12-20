package com.codingshuttle.securityapp.dtos;

import lombok.Data;

@Data
public class SignUpDto {
    private String email;
    private String name;
    private String password;
}
