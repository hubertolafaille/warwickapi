package io.github.hubertolafaille.warwickapi.service;

import io.github.hubertolafaille.warwickapi.dto.UserCreationResponseDTO;
import io.github.hubertolafaille.warwickapi.entity.RoleEntity;
import io.github.hubertolafaille.warwickapi.entity.UserEntity;
import io.github.hubertolafaille.warwickapi.enumeration.RoleEnum;
import io.github.hubertolafaille.warwickapi.repository.UserRepository;
import io.github.hubertolafaille.warwickapi.security.PasswordEncoder;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserEntity findUserEntityByEmail(String email) throws EntityNotFoundException {
        return userRepository.findUserEntityByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Email not found : " + email));
    }

    public UserCreationResponseDTO addUser(String email, String password) throws EntityExistsException, EntityNotFoundException {
        if (userRepository.existsByEmail(email)){
            throw new EntityExistsException("User already exist : " + email);
        }

        RoleEntity roleEntity = roleService.findRoleEntityByRoleEnum(RoleEnum.USER);

        Set<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(roleEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(password));
        userEntity.setRoles(roleEntitySet);
        return new UserCreationResponseDTO(userRepository.save(userEntity).getEmail());
    }
}
