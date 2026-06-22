package com.condominios.sgc.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record PropietarioApartamentoDetailResponse(
    Long id,
    Integer numero,
    BigDecimal metraje,
    Boolean derechoEstacionamiento,
    String torreNombre,
    Integer pisoNumero,
    int totalInquilinos,
    int totalVehiculos,
    List<PropietarioInquilinoResponse> inquilinos,
    List<PropietarioVehiculoResponse> vehiculos
) {
}
