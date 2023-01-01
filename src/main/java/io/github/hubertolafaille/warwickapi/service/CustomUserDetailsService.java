package io.github.hubertolafaille.warwickapi.service;

import io.github.hubertolafaille.warwickapi.entity.RoleEntity;
import io.github.hubertolafaille.warwickapi.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity;
        try {
            userEntity = userService.findUserEntityByEmail(username);
        } catch (EntityNotFoundException e){
            throw new UsernameNotFoundException("Username not found : " + username);
        }
        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                mapRoleEntitySetToGrantedAuthorityCollection(userEntity.getRoles()));
    }

    private Collection<GrantedAuthority> mapRoleEntitySetToGrantedAuthorityCollection(Set<RoleEntity> roleEntityList){
        return roleEntityList.stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName().name()))
                .collect(Collectors.toList());
    }
}
