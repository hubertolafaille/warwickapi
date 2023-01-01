package io.github.hubertolafaille.warwickapi.customexception;

public class UserEntityAlreadyExistsException extends RuntimeException {
    public UserEntityAlreadyExistsException(String message) {
        super(message);
    }
}
