package io.github.hubertolafaille.warwickapi.service;

import io.github.hubertolafaille.warwickapi.entity.RoleEntity;
import io.github.hubertolafaille.warwickapi.enumeration.RoleEnum;
import io.github.hubertolafaille.warwickapi.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity findRoleEntityByRoleEnum(RoleEnum roleEnum) throws EntityNotFoundException {
        return roleRepository.findRoleEntityByName(roleEnum)
                .orElseThrow(() -> new EntityNotFoundException("Role not found : " + roleEnum.name()));
    }
}
