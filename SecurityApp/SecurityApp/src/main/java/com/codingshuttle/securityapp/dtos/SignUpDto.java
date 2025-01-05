package com.codingshuttle.securityapp.dtos;

import com.codingshuttle.securityapp.entities.enums.Permission;
import com.codingshuttle.securityapp.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String name;
    private String password;
    private Set<Role> roles;
}
