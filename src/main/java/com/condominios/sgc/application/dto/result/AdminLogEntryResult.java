package com.condominios.sgc.application.dto.result;

import java.math.BigDecimal;
import java.time.Instant;

public record AdminLogEntryResult(
    Long id,
    String tipo,
    String placa,
    String ocupante,
    String datosInquilino,
    String metodo,
    Instant fechaEntrada,
    Instant fechaSalida,
    String solicitante,
    String nombreSolicitante,
    String dniSolicitante,
    BigDecimal penalizacion,
    Instant fechaPrestamo,
    Instant fechaDevolucion,
    Long idCondominio
) {
}
