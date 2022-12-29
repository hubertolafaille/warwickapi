package io.github.hubertolafaille.warwickapi.security;

import io.github.hubertolafaille.warwickapi.entity.RoleEntity;
import io.github.hubertolafaille.warwickapi.entity.UserEntity;
import io.github.hubertolafaille.warwickapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found : " + username));
        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                mapRoleEntityListToGrantedAuthorityCollection(userEntity.getRoles()));
    }

    private Collection<GrantedAuthority> mapRoleEntityListToGrantedAuthorityCollection(List<RoleEntity> roleEntityList){
        return roleEntityList.stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName().name()))
                .collect(Collectors.toList());
    }
}
