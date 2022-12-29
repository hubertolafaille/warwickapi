package io.github.hubertolafaille.warwickapi.repository;

import io.github.hubertolafaille.warwickapi.entity.RoleEntity;
import io.github.hubertolafaille.warwickapi.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findRoleEntityByName(RoleEnum name);
}
