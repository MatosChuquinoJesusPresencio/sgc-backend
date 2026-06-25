package com.condominios.sgc.application.dto.result;

import java.math.BigDecimal;
import java.util.List;

public record AdminApartamentoDetailResult(
    Long id,
    Integer numero,
    BigDecimal metraje,
    Boolean derechoEstacionamiento,
    Long idPropietario,
    String nombrePropietario,
    String torreNombre,
    Integer pisoNumero,
    List<AdminInquilinoResult> inquilinos
) {
}
