package io.github.hubertolafaille.warwickapi.controller;

import io.github.hubertolafaille.warwickapi.customexception.RoleEntityNotFoundException;
import io.github.hubertolafaille.warwickapi.customexception.UserEntityAlreadyExistsException;
import io.github.hubertolafaille.warwickapi.customexception.UserEntityNotFoundException;
import io.github.hubertolafaille.warwickapi.dto.SignInRequestDTO;
import io.github.hubertolafaille.warwickapi.dto.SignInResponseDTO;
import io.github.hubertolafaille.warwickapi.dto.SignUpRequestDTO;
import io.github.hubertolafaille.warwickapi.dto.SignUpResponseDTO;
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
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) throws RoleEntityNotFoundException, UserEntityNotFoundException, UserEntityAlreadyExistsException {
        log.info("(POST) : /api/auth/signUp -> [REQUEST] -> {}", signUpRequestDTO.email());
        ResponseEntity<SignUpResponseDTO> responseEntity = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signUp(signUpRequestDTO.email(), signUpRequestDTO.password()));
        log.info("(POST) : /api/auth/signUp -> [CREATED] -> {}", signUpRequestDTO.email());
        return responseEntity;
    }

    @PostMapping("/signIn")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO){
        log.info("(POST) : /api/auth/signIn -> [REQUEST] -> {}", signInRequestDTO.email());
        ResponseEntity<SignInResponseDTO> responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.signIn(signInRequestDTO.email(), signInRequestDTO.password()));
        log.info("(POST) : /api/auth/signIn -> [OK] -> {}", signInRequestDTO.email());
        return responseEntity;
    }

}
