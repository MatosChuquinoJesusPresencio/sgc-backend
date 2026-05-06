package com.condominios.sgc.web.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
    int status,
    String error,
    String message,
    LocalDateTime timestamp
) {
    public static ErrorResponse of(int status, String error, String message) {
        return new ErrorResponse(status, error, message, LocalDateTime.now());
    }

    public static ErrorResponse notFound(String message) {
        return new ErrorResponse(404, "Not Found", message, LocalDateTime.now());
    }

    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse(400, "Bad Request", message, LocalDateTime.now());
    }
}
