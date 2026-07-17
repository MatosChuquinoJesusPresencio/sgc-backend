package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.math.BigDecimal;

public record AdminLogEntryResponse(
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
    Long idCondominio,
    Long idVehiculo,
    Long idEstacionamiento,
    String nombrePropietario,
    String torreNombre,
    Integer numeroDepartamento,
    String marca,
    String modelo,
    String color,
    String tipoVehiculo
) {
}
