package io.github.hubertolafaille.warwickapi.customexception;

import io.github.hubertolafaille.warwickapi.dto.APIErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({RoleEntityNotFoundException.class})
    public ResponseEntity<APIErrorResponseDTO> roleEntityNotFoundException(RoleEntityNotFoundException roleEntityNotFoundException){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(generateAPIError(roleEntityNotFoundException, httpStatus), httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UserEntityNotFoundException.class})
    public ResponseEntity<APIErrorResponseDTO> userEntityNotFoundException(UserEntityNotFoundException userEntityNotFoundException){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(generateAPIError(userEntityNotFoundException, httpStatus), httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UserEntityAlreadyExistsException.class})
    public ResponseEntity<APIErrorResponseDTO> userEntityAlreadyExistsException(UserEntityAlreadyExistsException userEntityAlreadyExistsException){
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        return new ResponseEntity<>(generateAPIError(userEntityAlreadyExistsException, httpStatus), httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public ResponseEntity<APIErrorResponseDTO> exception(Exception exception){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(generateAPIError(exception,"An unknown error has occurred", httpStatus), httpStatus);
    }

    private APIErrorResponseDTO generateAPIError(Exception e, String errorMessage, HttpStatus httpStatus){
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        log.error("{} {} -> ERROR : {}", httpServletRequest.getMethod(), httpServletRequest.getServletPath(), e.getMessage());
        return new APIErrorResponseDTO(httpStatus.value(),
                httpStatus.name(),
                httpServletRequest.getMethod(),
                httpServletRequest.getServletPath(),
                errorMessage,
                LocalDateTime.now());
    }

    private APIErrorResponseDTO generateAPIError(Exception e, HttpStatus httpStatus){
        return generateAPIError(e, e.getMessage(), httpStatus);
    }
}
