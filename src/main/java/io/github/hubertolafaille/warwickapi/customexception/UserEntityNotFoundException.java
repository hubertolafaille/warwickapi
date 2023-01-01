package io.github.hubertolafaille.warwickapi.customexception;

public class UserEntityNotFoundException extends RuntimeException {
    public UserEntityNotFoundException(String message) {
        super(message);
    }
}
