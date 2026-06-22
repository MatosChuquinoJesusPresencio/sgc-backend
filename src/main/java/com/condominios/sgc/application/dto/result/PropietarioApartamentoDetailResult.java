package com.condominios.sgc.application.dto.result;

import java.math.BigDecimal;
import java.util.List;

public record PropietarioApartamentoDetailResult(
    Long id,
    Integer numero,
    BigDecimal metraje,
    Boolean derechoEstacionamiento,
    String torreNombre,
    Integer pisoNumero,
    int totalInquilinos,
    int totalVehiculos,
    List<PropietarioInquilinoResult> inquilinos,
    List<PropietarioVehiculoResult> vehiculos
) {
}
