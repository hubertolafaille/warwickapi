package io.github.hubertolafaille.warwickapi.service;

import io.github.hubertolafaille.warwickapi.dto.UserCreationResponseDTO;
import io.github.hubertolafaille.warwickapi.entity.RoleEntity;
import io.github.hubertolafaille.warwickapi.entity.UserEntity;
import io.github.hubertolafaille.warwickapi.enumeration.RoleEnum;
import io.github.hubertolafaille.warwickapi.repository.RoleRepository;
import io.github.hubertolafaille.warwickapi.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCreationResponseDTO addUser(String email, String password) {
        if (userRepository.existsByEmail(email)){
            throw new EntityExistsException("User already exist : " + email);
        }

        RoleEntity roleEntity = roleRepository.findRoleEntityByName(RoleEnum.USER)
                .orElseThrow(() -> new EntityNotFoundException("Role not found : " + RoleEnum.USER.name()));

        Set<RoleEntity> roleEntityList = new HashSet<>();
        roleEntityList.add(roleEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRoles(roleEntityList);
        UserEntity user = userRepository.save(userEntity);
        System.out.println(user.getRoles());
        return new UserCreationResponseDTO(email,"password");
    }
}
