package com.amalitech.securesail.amalisecuresail.user_management.services;

import com.amalitech.securesail.amalisecuresail.user_management.models.Role;
import com.amalitech.securesail.amalisecuresail.global.constants.OperationResultConstants;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.requests.RoleRequest;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.response.RoleResponse;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.EntityAlreadyExistsException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.EntityNotFoundException;
import com.amalitech.securesail.amalisecuresail.user_management.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;
    private static final String ROLE_NOT_FOUND = "Role not found";

    public ResponseEntity<RoleResponse> create(RoleRequest request) {
        Role role = new Role();
        boolean isRoleExists = roleRepository.existsByNameIgnoreCase(request.name());
        if (isRoleExists) {
            throw new EntityAlreadyExistsException("Role already exists");
        }
        role.setName(request.name());
        role.setDescription(request.description());
        role.setPermissions(request.permissions());
        Role newRole = roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RoleResponse(OperationResultConstants.SUCCESS, newRole.getId()));
    }

    public ResponseEntity<RoleResponse> update(RoleRequest request) {
        Role role = roleRepository.findByNameIgnoreCase(request.name()).orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
        role.setName(request.name());
        role.setDescription(request.description());
        role.setPermissions(request.permissions());
        Role updatedRole = roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.OK).body(new RoleResponse(OperationResultConstants.SUCCESS, updatedRole.getId()));
    }

    public ResponseEntity<RoleResponse> delete(String name) {
        Role role = roleRepository.findByNameIgnoreCase(name).orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
        roleRepository.delete(role);
        return ResponseEntity.status(HttpStatus.OK).body(new RoleResponse(OperationResultConstants.SUCCESS, role.getId()));
    }

    public ResponseEntity<RoleResponse> get(String name) {
        Role role = roleRepository.findByNameIgnoreCase(name).orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).body(new RoleResponse(OperationResultConstants.SUCCESS, role.getId()));
    }
}