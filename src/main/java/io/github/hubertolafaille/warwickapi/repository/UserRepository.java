package io.github.hubertolafaille.warwickapi.repository;

import io.github.hubertolafaille.warwickapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUserEntityByEmail(String email);
    Boolean existsByEmail(String email);
}
