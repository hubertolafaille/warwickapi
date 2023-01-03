package io.github.hubertolafaille.warwickapi.service;

import io.github.hubertolafaille.warwickapi.customexception.RoleEntityNotFoundException;
import io.github.hubertolafaille.warwickapi.customexception.UserEntityAlreadyExistsException;
import io.github.hubertolafaille.warwickapi.dto.SignInResponseDTO;
import io.github.hubertolafaille.warwickapi.dto.SignUpResponseDTO;
import io.github.hubertolafaille.warwickapi.entity.RoleEntity;
import io.github.hubertolafaille.warwickapi.entity.UserEntity;
import io.github.hubertolafaille.warwickapi.enumeration.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    public SignUpResponseDTO signUp(String email, String password) throws UserEntityAlreadyExistsException, RoleEntityNotFoundException {
        if (userService.existsByEmail(email)){
            throw new UserEntityAlreadyExistsException("User already exist : " + email);
        }

        RoleEntity roleEntity = roleService.findRoleEntityByRoleEnum(RoleEnum.USER);

        Set<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(roleEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(bCryptPasswordEncoder.encode(password));
        userEntity.setRoles(roleEntitySet);
        return new SignUpResponseDTO(userService.saveSignUp(userEntity));
    }

    public SignInResponseDTO signIn(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new SignInResponseDTO(email);
    }
}
