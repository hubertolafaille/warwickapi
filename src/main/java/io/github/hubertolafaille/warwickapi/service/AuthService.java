package io.github.hubertolafaille.warwickapi.service;

import io.github.hubertolafaille.warwickapi.customexception.RoleEntityNotFoundException;
import io.github.hubertolafaille.warwickapi.customexception.UserEntityAlreadyExistsException;
import io.github.hubertolafaille.warwickapi.dto.UserCreationResponseDTO;
import io.github.hubertolafaille.warwickapi.entity.RoleEntity;
import io.github.hubertolafaille.warwickapi.entity.UserEntity;
import io.github.hubertolafaille.warwickapi.enumeration.RoleEnum;
import io.github.hubertolafaille.warwickapi.security.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserCreationResponseDTO signUp(String email, String password) throws UserEntityAlreadyExistsException, RoleEntityNotFoundException {
        if (userService.existsByEmail(email)){
            throw new UserEntityAlreadyExistsException("User already exist : " + email);
        }

        RoleEntity roleEntity = roleService.findRoleEntityByRoleEnum(RoleEnum.USER);

        Set<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(roleEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(password));
        userEntity.setRoles(roleEntitySet);
        return new UserCreationResponseDTO(userService.saveSignUp(userEntity));
    }
}
