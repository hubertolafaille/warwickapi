package io.github.hubertolafaille.warwickapi.controller;

import io.github.hubertolafaille.warwickapi.customexception.RoleEntityNotFoundException;
import io.github.hubertolafaille.warwickapi.customexception.UserEntityAlreadyExistsException;
import io.github.hubertolafaille.warwickapi.customexception.UserEntityNotFoundException;
import io.github.hubertolafaille.warwickapi.dto.UserCreationRequestDTO;
import io.github.hubertolafaille.warwickapi.dto.UserCreationResponseDTO;
import io.github.hubertolafaille.warwickapi.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserCreationResponseDTO> addUser(@RequestBody UserCreationRequestDTO userCreationRequestDTO) throws RoleEntityNotFoundException, UserEntityNotFoundException, UserEntityAlreadyExistsException {
        log.info("POST /api/auth/signUp -> REQUEST : {}", userCreationRequestDTO.email());
            ResponseEntity<UserCreationResponseDTO> responseEntity = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(authService.signUp(userCreationRequestDTO.email(), userCreationRequestDTO.password()));
            log.info("POST /api/auth/signUp -> CREATED : {}", userCreationRequestDTO.email());
            return responseEntity;
    }

}
