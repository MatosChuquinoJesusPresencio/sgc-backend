package com.condominios.sgc.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int status,
        String error,
        String timestamp,
        String path
) {
    public ErrorResponse(int status, String error, String path) {
        this(status, error, LocalDateTime.now().toString(), path);
    }
}
