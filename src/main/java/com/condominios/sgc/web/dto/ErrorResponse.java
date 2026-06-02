package com.condominios.sgc.web.dto;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

public record ErrorResponse(int status, String message, Instant timestamp, Map<String, String> errors) {

    public ErrorResponse {
        if (errors == null) {
            errors = Collections.emptyMap();
        }
    }

    public static ErrorResponse of(int status, String message, Instant timestamp) {
        return new ErrorResponse(status, message, timestamp, null);
    }

    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message, Instant.now(), null);
    }

    public static ErrorResponse notFound(String message) {
        return new ErrorResponse(404, message, Instant.now(), null);
    }

    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse(400, message, Instant.now(), null);
    }

    public static ErrorResponse validationError(Map<String, String> errors) {
        return new ErrorResponse(400, "Error de validacion", Instant.now(), errors);
    }
}
