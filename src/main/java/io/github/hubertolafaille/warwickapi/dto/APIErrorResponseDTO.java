package io.github.hubertolafaille.warwickapi.dto;

import java.time.LocalDateTime;

public record APIErrorResponseDTO(int errorCode, String errorName, String method, String path, String errorMessage, LocalDateTime time) {
}
