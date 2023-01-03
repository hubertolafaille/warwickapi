package io.github.hubertolafaille.warwickapi.service;

import io.github.hubertolafaille.warwickapi.customexception.UserEntityNotFoundException;
import io.github.hubertolafaille.warwickapi.entity.UserEntity;
import io.github.hubertolafaille.warwickapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findUserEntityByEmail(String email) throws UserEntityNotFoundException {
        return userRepository.findUserEntityByEmail(email).orElseThrow(
                () -> new UserEntityNotFoundException("Email not found : " + email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public String saveSignUp(UserEntity userEntity){
        return userRepository.save(userEntity).getEmail();
    }
}
