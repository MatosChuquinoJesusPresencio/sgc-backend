package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarOcupantesRequest(
    @NotNull @Valid List<InquilinoEntry> inquilinos
) {
    public record InquilinoEntry(
        @NotBlank String nombres,
        @NotBlank String apellidos,
        @NotBlank String tipoDocumento,
        @NotBlank String numeroDocumento
    ) {}
}
