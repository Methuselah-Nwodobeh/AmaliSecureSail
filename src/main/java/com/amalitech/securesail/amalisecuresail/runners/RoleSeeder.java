package com.amalitech.securesail.amalisecuresail.runners;

import com.amalitech.securesail.amalisecuresail.user_management.dtos.requests.RoleRequest;
import com.amalitech.securesail.amalisecuresail.user_management.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleSeeder {
    private final RoleService roleService;

    public void seedRoles() {
        RoleRequest basicUser = new RoleRequest(
                "USER",
                "Regular users are individuals who use the SSO platform to authenticate and access applications and services.",
                List.of("ACCESS_USER")
        );
        seedRole(basicUser);

        RoleRequest admin = new RoleRequest(
                "ADMIN",
                "Administrators have full control over the OpenID Connect SSO platform. They can manage users, clients, permissions, and configuration settings.",
                List.of("ACCESS_ADMIN", "PROMOTE_USER", "DEMOTE_USER", "BLOCK_USER", "REMOVE_USER_SCOPE", "CRUD_USER", "CRUD_ROLE", "CRUD_PERMISSION", "CRUD_CLIENT")
        );
        seedRole(admin);

        RoleRequest developer = new RoleRequest(
                "Developer",
                "Developers may have special privileges for managing client applications and integrations with the OpenID Connect SSO platform.",
                List.of("DYNAMIC")
        );
        seedRole(developer);

        RoleRequest superAdmin = new RoleRequest(
                "SUPER_ADMIN",
                "The super admin is an admin with all privileges. It can perform any action on the platform. This is a permanent member whose access can't be revoked.",
                List.of("ACCESS_ADMIN", "PROMOTE_USER", "DEMOTE_USER", "BLOCK_USER", "REMOVE_USER_SCOPE", "CRUD_USER", "CRUD_ADMIN", "CRUD_ROLE", "CRUD_PERMISSION", "CRUD_CLIENT", "IMMORTAL")
        );
        seedRole(superAdmin);
    }

    private void seedRole(RoleRequest roleRequest) {
        log.info("////////////////////////////////////////////////////////////////////////////////////");
        log.info("Seeding roles");

        try {
            log.info("role about to create");
            roleService.create(roleRequest);
            log.info("role created successfully");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}