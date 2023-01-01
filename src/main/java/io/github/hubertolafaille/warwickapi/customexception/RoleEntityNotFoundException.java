package io.github.hubertolafaille.warwickapi.customexception;

public class RoleEntityNotFoundException extends RuntimeException {
    public RoleEntityNotFoundException(String message) {
        super(message);
    }
}
