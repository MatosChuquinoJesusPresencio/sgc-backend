package com.condominios.sgc.web.dto;

import java.time.Instant;

public record ErrorResponse(int status, String message, Instant timestamp) {

    public static ErrorResponse of(int status, String message, Instant timestamp) {
        return new ErrorResponse(status, message, timestamp);
    }

    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message, Instant.now());
    }

    public static ErrorResponse notFound(String message) {
        return new ErrorResponse(404, message, Instant.now());
    }

    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse(400, message, Instant.now());
    }
}
