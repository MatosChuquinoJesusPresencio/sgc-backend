package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record CarritoPrestadoResumen(
    Long id,
    String codigoCarrito,
    String nombreSolicitante,
    String dniSolicitante,
    Instant fechaPrestamo,
    Long idApartamento
) {}
