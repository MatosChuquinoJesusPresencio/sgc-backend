package com.condominios.sgc.application.dto.result;

import java.math.BigDecimal;

public record AdminLogEntryResult(
    Long id,
    String tipo,
    String placa,
    String ocupante,
    String datosInquilino,
    String metodo,
    String fechaEntrada,
    String fechaSalida,
    String solicitante,
    String nombreSolicitante,
    String dniSolicitante,
    BigDecimal penalizacion,
    String fechaPrestamo,
    String fechaDevolucion,
    Long idCondominio
) {
}
