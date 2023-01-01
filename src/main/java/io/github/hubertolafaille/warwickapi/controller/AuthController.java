package io.github.hubertolafaille.warwickapi.controller;

import io.github.hubertolafaille.warwickapi.dto.UserCreationRequestDTO;
import io.github.hubertolafaille.warwickapi.dto.UserCreationResponseDTO;
import io.github.hubertolafaille.warwickapi.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> addUser(@RequestBody UserCreationRequestDTO userCreationRequestDTO){
        log.info("POST /api/auth/addUser -> REQUEST : email = {}", userCreationRequestDTO.email());
        try {
            ResponseEntity<UserCreationResponseDTO> responseEntity = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userService.addUser(userCreationRequestDTO.email(), userCreationRequestDTO.password()));
            log.info("POST /api/auth/addUser -> CREATED : email = {}", userCreationRequestDTO.email());
            return responseEntity;
        } catch (EntityExistsException e){
            log.error("POST /api/auth/addUser -> ERROR : {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(e.getMessage());
        } catch (EntityNotFoundException e){
            log.error("POST /api/auth/addUser -> ERROR : {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}
