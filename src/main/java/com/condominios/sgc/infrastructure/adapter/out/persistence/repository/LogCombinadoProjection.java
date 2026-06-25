package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.time.Instant;
import java.math.BigDecimal;

public interface LogCombinadoProjection {
    Long getId();
    String getTipoLog();

    // Campos Vehiculo
    String getPlaca();
    String getOcupante();
    String getDatosInquilino();
    String getMetodo();
    Instant getFechaEntrada();
    Instant getFechaSalida();

    // Campos Carrito
    String getSolicitante();
    String getNombreSolicitante();
    String getDniSolicitante();
    BigDecimal getPenalizacion();
    Instant getFechaPrestamo();
    Instant getFechaDevolucion();

    Long getCondominioId();
}