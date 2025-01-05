package com.codingshuttle.securityapp.utils;

import com.codingshuttle.securityapp.entities.enums.Permission;
import com.codingshuttle.securityapp.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codingshuttle.securityapp.entities.enums.Permission.*;
import static com.codingshuttle.securityapp.entities.enums.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> permissionMap = Map.of(
            USER, Set.of(POST_VIEW, USER_VIEW),
            CREATOR, Set.of(POST_CREATE, USER_CREATE, POST_UPDATE, USER_UPDATE),
            ADMIN, Set.of(POST_VIEW, USER_VIEW,POST_CREATE, USER_CREATE, POST_UPDATE, USER_UPDATE,POST_DELETE,USER_DELETE)

    );

    public static Set<SimpleGrantedAuthority> getPermissions(Role role){
        return permissionMap
                .get(role)
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }

}
