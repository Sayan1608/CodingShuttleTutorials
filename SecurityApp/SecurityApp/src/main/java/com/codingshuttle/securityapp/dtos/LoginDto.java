package com.codingshuttle.securityapp.dtos;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
