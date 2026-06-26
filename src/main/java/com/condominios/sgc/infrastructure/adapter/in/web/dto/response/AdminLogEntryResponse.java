package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.math.BigDecimal;

public record AdminLogEntryResponse(
        Long id,
        String tipo,
        String placa,
        String ocupante,
        String datosInquilino,
        String metodo,
        java.time.Instant fechaEntrada,
        java.time.Instant fechaSalida,
        String solicitante,
        String nombreSolicitante,
        String dniSolicitante,
        BigDecimal penalizacion,
        java.time.Instant fechaPrestamo,
        java.time.Instant fechaDevolucion,
        Long idCondominio
) {
}
