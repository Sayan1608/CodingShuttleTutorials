package com.codingshuttle.securityapp.dtos;

import com.codingshuttle.securityapp.entities.enums.Roles;
import lombok.Data;

import javax.management.relation.Role;
import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String name;
    private String password;
    private Set<Roles> roles;
}
