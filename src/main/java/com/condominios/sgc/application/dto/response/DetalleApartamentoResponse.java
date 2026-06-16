package com.condominios.sgc.application.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record DetalleApartamentoResponse(
    Long id,
    Integer numero,
    BigDecimal metraje,
    Boolean derechoEstacionamiento,
    PropietarioResumen propietario,
    Integer piso,
    String torre,
    String condominio,
    List<InquilinoResumen> inquilinos,
    List<EstacionamientoResumen> estacionamientos
) {}
